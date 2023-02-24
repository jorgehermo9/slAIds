package es.hackUDC.slAIds.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.hackUDC.slAIds.model.services.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/prompt")
    public String prompt() {

        return ChatService.execute()
    }
}
