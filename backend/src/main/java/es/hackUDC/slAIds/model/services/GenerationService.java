package es.hackUDC.slAIds.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.entities.Index;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.PresentationDao;
import es.hackUDC.slAIds.model.entities.Slide;
import es.hackUDC.slAIds.model.services.ChatService.ChatService;
import es.hackUDC.slAIds.model.services.ChatService.PromptResponse;
import es.hackUDC.slAIds.model.services.transferObjects.IndexTransfer;
import es.hackUDC.slAIds.model.services.transferObjects.SlideText;

@Service
@Transactional
public class GenerationService {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private PresentationDao presentationDao;

    public PromptResponse<IndexTransfer> generateIndex(String indexPrompt, int numSlides) {

        String requestIndexPrompt = "Generate an index for a presentations about "
                + indexPrompt
                + " of length "
                + numSlides
                + " pages.";

        Optional<PromptResponse<IndexTransfer>> optIndex = chatService.execute(requestIndexPrompt, IndexTransfer.class);

        PromptResponse<IndexTransfer> index = optIndex.get();

        return index;

    }

    public PromptResponse<SlideText> generateSlideText(String slideTitle, String slidePrompt, String conversationId,
            String parentId, int minWords, int maxWords) {

        String requestSlidePrompt = "Generate and informative, condensed, direct, without repeating information "
        		+ "already given in this conversation, paragraph, between " 
        		+ minWords + " and " + maxWords + " words,"
                + " with the title \"" + slideTitle + "\" about: " + slidePrompt + ".";

        return (chatService.executeWithConversation(requestSlidePrompt, SlideText.class, conversationId, parentId)
                .get());

    }

    public Presentation generatePresentation(String presentationTitle, String presentationPrompt, int numSlides, int minWords, int maxWords, Presentation presentation) {

        //Presentation presentation = new Presentation();
        String parentId;
        String conversationId;

        presentation.setTitle(presentationTitle);
        presentation.setDescriptionPrompt(presentationPrompt);

        PromptResponse<IndexTransfer> responseIndex = generateIndex(presentationPrompt, numSlides);

        presentation.setIndex(new Index(responseIndex.response().getSlideTitles(),responseIndex.response().getSlideDescriptions()));
        parentId = responseIndex.parentId();
        conversationId = responseIndex.conversationId();

        List<Slide> slides = new ArrayList<Slide>();
        PromptResponse<SlideText> responseSlideText;

        for (int i = 0; i < numSlides; i++) {

            String slideTitle = presentation.getIndex().getSlideTitles().get(i);
            String slideDescription = presentation.getIndex().getSlideDescriptions().get(i);

            responseSlideText = generateSlideText(slideTitle, slideDescription, conversationId, parentId, minWords, maxWords);
            slides.add(new Slide(slideTitle, responseSlideText.response().getText(), (i + 1)));

            parentId = responseSlideText.parentId();
            conversationId = responseSlideText.conversationId();
        }

        presentation.setSlides(slides);

        presentationDao.save(presentation);
        
        return presentation;
    }
    
    public boolean isAvailable(Long presentationId) {
    	
    	Presentation presentation = presentationDao.findById(presentationId).get();
    	
    	return (Objects.isNull(presentation.getIndex()));
    	
    }
    
    public Presentation getGeneratedPresentation(Long presentationId) {
    	
    	Presentation presentation = presentationDao.findById(presentationId).get();
    	
    	return (presentation);
    }

}
