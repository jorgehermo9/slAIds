package es.hackUDC.slAIds.rest.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.hackUDC.slAIds.model.entities.ModelUser;
import es.hackUDC.slAIds.model.entities.ModelUserDao;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.PresentationDao;
import es.hackUDC.slAIds.model.exceptions.InstanceNotFoundException;
import es.hackUDC.slAIds.model.services.BuildPptService;
import es.hackUDC.slAIds.model.services.GenerationService;
import es.hackUDC.slAIds.rest.common.ErrorsDto;
import es.hackUDC.slAIds.rest.dtos.PresentationConversor;
import es.hackUDC.slAIds.rest.dtos.PresentationDto;
import es.hackUDC.slAIds.rest.dtos.generationRequestDto;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/presentations")
public class GenerationController {

    @Autowired
    private GenerationService generationService;

    @Autowired
    private PresentationDao presentationDao;

    @Autowired
    private BuildPptService buildPptService;

    @Autowired
    private ModelUserDao modelUserDao;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleIncorrectLoginException(InstanceNotFoundException exception) {

        return new ErrorsDto("The user: " + exception.getKey() + " does not exist");

    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorsDto handleIOException(IOException exception) {

        return new ErrorsDto("Error while generating the presentation: " + exception.getMessage());

    }

    @PostMapping("/generate")
    public Long generatePresentation(
            @RequestAttribute Long userId,
            @RequestBody generationRequestDto generationRequestDto) throws InstanceNotFoundException {

        float imgWidth = 360;
        float imgHeight = 220;

        Optional<ModelUser> modelUser = modelUserDao.findById(userId);
        if (!modelUser.isPresent()) {
            throw new InstanceNotFoundException("User not found", userId);
        }

        Presentation presentation = new Presentation();
        presentation.setModelUser(modelUser.get());
        presentation.setIsAvailable(false);
        presentation.setTitle(generationRequestDto.title());

        presentationDao.save(presentation);

        // Start a new thread to generate the presentation asynchronously
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Presentation generatedPresentation = generationService.generatePresentation(
                        generationRequestDto.title(),
                        generationRequestDto.prompt(),
                        generationRequestDto.numSlides(),
                        generationRequestDto.minWords(),
                        generationRequestDto.maxWords(),
                        generationRequestDto.bulletPoints(),
                        imgWidth,
                        imgHeight,
                        presentation);
                Presentation builtPresentation = buildPptService.buildPpt(generatedPresentation, imgWidth, imgHeight);
                presentation.setIsAvailable(true);
                presentationDao.save(builtPresentation);
            }
        });
        t1.start();

        return presentation.getId();

    }

    @GetMapping("/{presentationId}")
    public Presentation getGeneratedPresentation(@RequestAttribute Long userId,
            @PathVariable Long presentationId) throws InstanceNotFoundException {

        Optional<ModelUser> modelUser = modelUserDao.findById(userId);
        if (!modelUser.isPresent()) {
            throw new InstanceNotFoundException("User not found", userId);
        }

        Presentation presentation = generationService.getGeneratedPresentation(presentationId);

        if (presentation.getModelUser().getId() != userId) {
            throw new InstanceNotFoundException("User unhautorized", userId);
        }

        return presentation;
    }

    @GetMapping("/{presentationId}/is-available")
    public boolean isAvailable(
            @RequestAttribute Long userId,
            @PathVariable Long presentationId) throws InstanceNotFoundException {

        Optional<ModelUser> modelUser = modelUserDao.findById(userId);
        if (!modelUser.isPresent()) {
            throw new InstanceNotFoundException("User not found", userId);
        }

        Presentation presentation = generationService.getGeneratedPresentation(presentationId);

        if (presentation.getModelUser().getId() != userId) {
            throw new InstanceNotFoundException("User unhautorized", userId);
        }

        return generationService.isAvailable(presentationId);

    }

    @GetMapping("/{presentationId}/download/pptx")
    public ResponseEntity<Resource> downloadPptx(
            @PathVariable Long presentationId) throws IOException {
        Presentation presentation = generationService.getGeneratedPresentation(presentationId);

        byte[] pptx = presentation.getPptx();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pptx));

        return ResponseEntity.ok()
                .contentLength(pptx.length)
                .header("Content-Disposition", "attachment; filename=\"presentation.pptx\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{presentationId}/download/pdf")
    public ResponseEntity<Resource> downloadPdf(
            @PathVariable Long presentationId) throws IOException {
        Presentation presentation = generationService.getGeneratedPresentation(presentationId);
        byte[] pdf = presentation.getPdf();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdf));

        return ResponseEntity.ok()
                .contentLength(pdf.length)
                .header("Content-Disposition", "attachment")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    @GetMapping("/{presentationId}/pdf")
    public byte[] getPdf(
            @RequestAttribute Long userId,
            @PathVariable Long presentationId) throws IOException {
        Presentation presentation = generationService.getGeneratedPresentation(presentationId);

        return presentation.getPdf();

    }

    @GetMapping("/{presentationId}/pptx")
    public byte[] getPptx(
            @PathVariable Long presentationId) throws IOException {
        Presentation presentation = generationService.getGeneratedPresentation(presentationId);

        return presentation.getPptx();
    }

    @GetMapping("")
    public List<PresentationDto> getAllPresentations(@RequestAttribute Long userId)
            throws IOException, InstanceNotFoundException {

        List<Presentation> presentationList = presentationDao.findByModelUserId(userId);
        return PresentationConversor.toPresentationDtoList(presentationList);
    }
}
