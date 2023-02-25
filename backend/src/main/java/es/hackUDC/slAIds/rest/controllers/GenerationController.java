package es.hackUDC.slAIds.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/generate")
public class GenerationController {

    @Autowired
    private GenerationService generationService;

    @Autowired
    private PresentationDao presentationDao;
    
    @Autowired
    private BuildPptService buildPptService;

    @PostMapping("")
    public Long generatePresentation(
            @RequestBody generationRequestDto generationRequestDto) {


        Presentation presentation = new Presentation();
        presentationDao.save(presentation);
                
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                generationService.generatePresentation(
                		generationRequestDto.title(),
                        generationRequestDto.prompt(),
                        generationRequestDto.numSlides(),
                        generationRequestDto.minWords(),
                        generationRequestDto.maxWords(),
                        presentation);
            }
        });  
        t1.start();
        
        //buildPptService.buildPpt(presentation);
        
        return presentation.getId();

    }
    
    @GetMapping("/{presentationId}/is-available")
    public boolean isAvailable(
    	@PathVariable Long presentationId) {
    	
    	return (generationService.isAvailable(presentationId));
    	
    }
    
    @GetMapping("/{presentationId}")
    public Presentation getGeneratedPresentation(
    		@PathVariable Long presentationId) {
    	
    	return (generationService.getGeneratedPresentation(presentationId));
    	
    }
    

}
