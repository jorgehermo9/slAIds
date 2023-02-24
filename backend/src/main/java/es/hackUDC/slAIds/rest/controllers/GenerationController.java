package es.hackUDC.slAIds.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.services.GenerationService;
import es.hackUDC.slAIds.rest.dtos.generationRequestDto;

@RestController
@RequestMapping("/generate")
public class GenerationController {

    @Autowired
    private GenerationService generationService;

    @PostMapping("")
    public Presentation generatePresentation(
            @RequestBody generationRequestDto generationRequestDto) {

        Presentation presentation = generationService.generatePresentation(generationRequestDto.title(),
                generationRequestDto.prompt(),
                generationRequestDto.numSlides(),
                generationRequestDto.minWords(),
                generationRequestDto.maxWords());
        return presentation;

    }


}
