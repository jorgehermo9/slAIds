package es.hackUDC.slAIds.rest.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.PresentationDao;
import es.hackUDC.slAIds.model.services.BuildPptService;
import es.hackUDC.slAIds.model.services.GenerationService;
import es.hackUDC.slAIds.rest.dtos.generationRequestDto;

@RestController
@RequestMapping("/presentations")
public class GenerationController {

    @Autowired
    private GenerationService generationService;

    @Autowired
    private PresentationDao presentationDao;

    @Autowired
    private BuildPptService buildPptService;

    @PostMapping("/generate")
    public Long generatePresentation(
            @RequestBody generationRequestDto generationRequestDto) {

        Presentation presentation = new Presentation();
        presentationDao.save(presentation);

        System.out.println(presentation.getId());

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
                        presentation);
                Presentation builtPresentation = buildPptService.buildPpt(generatedPresentation);
                presentation.setIsAvailable(true);
                presentationDao.save(builtPresentation);
            }
        });
        t1.start();

        return presentation.getId();

    }

    @GetMapping("/{presentationId}")
    public Presentation getGeneratedPresentation(
            @PathVariable Long presentationId) {

        return generationService.getGeneratedPresentation(presentationId);
    }

    @GetMapping("/{presentationId}/is-available")
    public boolean isAvailable(
            @PathVariable Long presentationId) {

        return generationService.isAvailable(presentationId);

    }

    @GetMapping("/{presentationId}/download")
    public ResponseEntity<Resource> downloadPresentation(
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

}
