package es.hackUDC.slAIds.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.entities.Index;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.Slide;
import es.hackUDC.slAIds.model.services.ChatService.ChatService;
import es.hackUDC.slAIds.model.services.ChatService.PromptResponse;
import es.hackUDC.slAIds.model.services.transferObjects.SlideText;

@Service
@Transactional
public class GenerationService {

	@Autowired
	private ChatService chatService;

	public Index generateIndex(String indexPrompt, int numSlides) {
		
		String requestIndexPrompt = "Genera un indice para una presentación sobre " 
							+ indexPrompt 
							+ " de longitud " 
							+ numSlides 
							+ " paginas.";
		
		Optional<PromptResponse<Index>> optIndex = chatService.execute(requestIndexPrompt, Index.class);
		
		Index index = optIndex.get().response();
		
		return index;
		
	}
	
	public Slide generateSlide(String slideTitle, String slidePrompt, int numSlide) {
		
		String requestSlidePrompt = "Genera un parrafo informativo, de entre 50 y 60 palabras,"
				+ " de título " + slideTitle + " a cerca de " + slidePrompt + ".";
		
		
		Slide slide = new Slide();
		
		slide.setNumber(numSlide);
		slide.setTitle(slideTitle);
		slide.setText(chatService.execute(requestSlidePrompt, SlideText.class).get().response().getText());
		
		return slide;
				
	}
	
	public Presentation generatePresentation(String presentationTitle, String presentationPrompt, int numSlides) {
		
		Presentation presentation = new Presentation();
		
		presentation.setTitle(presentationTitle);
		presentation.setDescriptionPrompt(presentationPrompt);
		presentation.setIndex(generateIndex(presentationPrompt, numSlides));
		
		List<Slide> slides = new ArrayList<Slide>();
		
		for(int i = 0; i<=numSlides; i++) {
			slides.add(generateSlide(presentation.getIndex().getSlideTitles().get(i),
									presentation.getIndex().getSlideDescriptions().get(i),
									i));
		}
		
		presentation.setSlides(slides);
		
		return presentation;		
	}
	
	
	
}
