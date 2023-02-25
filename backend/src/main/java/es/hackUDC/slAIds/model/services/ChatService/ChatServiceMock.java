package es.hackUDC.slAIds.model.services.ChatService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.hackUDC.slAIds.model.services.TransferObjects.IndexTransfer;
import es.hackUDC.slAIds.model.services.TransferObjects.SlideText;

@Service
public class ChatServiceMock {

	
	public Optional<PromptResponse<IndexTransfer>> execute(String request, Class targetClass) {
		
			Optional opt = Optional.of(mockIndex());
		
			return opt;
	}
	
	public  Optional<PromptResponse<SlideText>> executeWithConversation(String request, Class targetClass, String conversationId, String parentId) {
		
			Optional opt = Optional.of(mockSlide());
		
			return opt;
	}
	
	public PromptResponse<IndexTransfer> mockIndex(){
		
		List<String> titles = Arrays.asList("uno","dos","tres","cuatro","cinco");
		List<String> descriptions = Arrays.asList("d1","d2","d3","d4","d5");
		
		IndexTransfer index = new IndexTransfer(titles, descriptions);
		
		PromptResponse<IndexTransfer> response = new PromptResponse<IndexTransfer>(index,null,null,null);
		
		return response;
	}
	
	public PromptResponse<SlideText> mockSlide(){
		
		SlideText text = new SlideText("texto");
		
		PromptResponse<SlideText> response = new PromptResponse<SlideText>(text,null,null,null);
		
		return (response);
		
		
		
	}
	
}
