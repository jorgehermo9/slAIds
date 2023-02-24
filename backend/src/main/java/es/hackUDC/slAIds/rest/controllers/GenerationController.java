package es.hackUDC.slAIds.rest.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate")
public class GenerationController {
	
	@PostMapping("")
	public void generatePresentation(@RequestBody String generationRequestDto) {
		
		
		
	}
	

}
