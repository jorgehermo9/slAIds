package es.hackUDC.slAIds.model.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.entities.Index;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.Slide;

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
							+ " paginas";
		
		Index index = null;;
		
		return index;
		
	}
	
	public Slide generateSlide(String slideTitle, String slidePrompt, int numSlide) {
		
		String requestSlidePrompt = "Genera un parrafo informativo, de entre 50 y 60 palabras,"
				+ " de título " + slideTitle + " a cerca de " + slidePrompt;
		
		
		Slide slide = new Slide();
		
		slide.setNumber(numSlide);
		slide.setTitle(slideTitle);
		slide.setText("respuestaGPT");
		
		
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
