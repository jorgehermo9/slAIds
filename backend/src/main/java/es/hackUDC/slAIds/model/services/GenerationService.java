package es.hackUDC.slAIds.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.entities.Index;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.PresentationDao;
import es.hackUDC.slAIds.model.entities.Slide;
import es.hackUDC.slAIds.model.services.ChatService.ChatServiceMock;
import es.hackUDC.slAIds.model.services.ChatService.PromptResponse;
import es.hackUDC.slAIds.model.services.ImageService.ImageServiceImpl;
import es.hackUDC.slAIds.model.services.TransferObjects.IndexTransfer;
import es.hackUDC.slAIds.model.services.TransferObjects.SlideText;

@Service
@Transactional
public class GenerationService {

  @Autowired
  // private ChatService chatService;
  private ChatServiceMock chatService;

  @Autowired
  private ImageServiceImpl imageService;

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

  public PromptResponse<SlideText> generateSlide(String slideTitle, String slidePrompt, String conversationId,
      String parentId, int minWords, int maxWords, boolean bulletPoints) {

    if (bulletPoints) {
      return generateSlideBullets(slideTitle, slidePrompt, conversationId, parentId, minWords, maxWords);
    } else {
      return generateSlideText(slideTitle, slidePrompt, conversationId, parentId, minWords, maxWords);
    }

  }

  public PromptResponse<SlideText> generateSlideText(String slideTitle, String slidePrompt, String conversationId,
      String parentId, int minWords, int maxWords) {

    String requestSlidePrompt = "Generate an informative, condensed, direct, without repeating information "
        + "already given in this conversation, paragraph, between "
        + minWords + " and " + maxWords + " words,"
        + " with the title \"" + slideTitle + "\" about: " + slidePrompt + ".";

    return (chatService.executeWithConversation(requestSlidePrompt, SlideText.class, conversationId, parentId)
        .get());

  }

  public PromptResponse<SlideText> generateSlideBullets(String slideTitle, String slidePrompt, String conversationId,
      String parentId, int minWords, int maxWords) {

    String requestSlidePrompt = "Generate between 3 and 5 informative, condense, direct, sentences,"
        + "without reapeating any information already given in this conversation, with the title \""
        + slideTitle + "\" and about: " + slidePrompt + ". In the text separate"
        + " each sentence by a newline. The total length of the text must be between "
        + minWords + " and " + maxWords + " words.";

    return (chatService.executeWithConversation(requestSlidePrompt, SlideText.class, conversationId, parentId)
        .get());

  }

  public Presentation generatePresentation(String presentationTitle, String presentationPrompt, int numSlides,
      int minWords, int maxWords, boolean bulletPoints, float imgWidth, float imgHeight,
      Presentation presentation) {

    // Presentation presentation = new Presentation();
    String parentId;
    String conversationId;
    byte[] img = null;

    presentation.setTitle(presentationTitle);
    presentation.setDescriptionPrompt(presentationPrompt);

    img = imageService.getImage(presentationPrompt, imgWidth, imgHeight).get();
    presentation.setFrontImg(img);

    PromptResponse<IndexTransfer> responseIndex = generateIndex(presentationPrompt, numSlides);

    presentation.setIndex(
        new Index(responseIndex.response().getSlideTitles(), responseIndex.response().getSlideDescriptions()));
    parentId = responseIndex.parentId();
    conversationId = responseIndex.conversationId();

    List<Slide> slides = new ArrayList<Slide>();
    PromptResponse<SlideText> responseSlideText;

    for (int i = 0; i < numSlides; i++) {

      String slideTitle = presentation.getIndex().getSlideTitles().get(i);
      String slideDescription = presentation.getIndex().getSlideDescriptions().get(i);

      responseSlideText = generateSlide(slideTitle, slideDescription, conversationId, parentId, minWords,
          maxWords, bulletPoints);

      String imagePrompt = "Inspirative beautiful photo about "+presentation.getTitle()+", "+ slideTitle;
      img = imageService.getImage(imagePrompt, imgWidth, imgHeight).get();

      slides.add(new Slide(slideTitle, responseSlideText.response().getText(), (i + 1), img));

      parentId = responseSlideText.parentId();
      conversationId = responseSlideText.conversationId();
    }

    presentation.setSlides(slides);

    return presentation;
  }

  public boolean isAvailable(Long presentationId) {

    Presentation presentation = presentationDao.findById(presentationId).get();

    return presentation.getIsAvailable();

  }

  public Presentation getGeneratedPresentation(Long presentationId) {

    Presentation presentation = presentationDao.findById(presentationId).get();

    return presentation;
  }

}
