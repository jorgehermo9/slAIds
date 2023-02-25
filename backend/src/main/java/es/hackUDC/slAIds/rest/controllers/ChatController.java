package es.hackUDC.slAIds.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.hackUDC.slAIds.model.services.ImageService.ImageServiceStableDiffusion;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ImageServiceStableDiffusion imageServiceStableDiffusion;

    @GetMapping("/prompt")
    public String prompt() {

        imageServiceStableDiffusion.getImage("maltese puppy");

        return "Hello";
    }
}
