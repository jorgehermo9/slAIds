package es.hackUDC.slAIds.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.entities.Index;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.PresentationDao;
import es.hackUDC.slAIds.model.entities.Slide;
import es.hackUDC.slAIds.model.services.ChatService.Chat;
import es.hackUDC.slAIds.model.services.ChatService.ChatService;
import es.hackUDC.slAIds.model.services.ImageService.ImageServiceImpl;
import es.hackUDC.slAIds.model.services.TransferObjects.IndexTransfer;
import es.hackUDC.slAIds.model.services.TransferObjects.SlideText;

@Service
@Transactional
public class GenerationService {

    @Autowired
    Environment env;

    @Autowired
    private ChatService chatService;
    // private ChatServiceMock chatService;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private PresentationDao presentationDao;

    public IndexTransfer generateIndex(Chat chat, String indexPrompt, int numSlides) {

        String requestIndexPrompt = "Generate an index for a presentation about \""
                + indexPrompt
                + "\" of length "
                + numSlides
                + " pages. The text of each slide description must not start with \"This slide...\" or similar.";

        // TODO: execute or executeTemporary here? With temporary cost is almost a half,
        // but maybe the prompt is not as good...
        Optional<IndexTransfer> index = chatService.executeTemporary(chat, requestIndexPrompt, IndexTransfer.class);

        return index.get();

    }

    public SlideText generateSlide(Chat chat, String slideTitle, String slidePrompt, int minWords, int maxWords,
            boolean bulletPoints) {

        if (bulletPoints) {
            return generateSlideBullets(chat, slideTitle, slidePrompt, minWords, maxWords);
        } else {
            return generateSlideText(chat, slideTitle, slidePrompt, minWords, maxWords);
        }

    }

    public SlideText generateSlideText(Chat chat, String slideTitle, String slidePrompt, int minWords, int maxWords) {

        String requestSlidePrompt = "Generate an informative, condensed, direct paragraph for a presentation slide, between "
                + minWords + " and " + maxWords + " words,"
                + " with the title \"" + slideTitle + "\" about: \"" + slidePrompt + "\"."
                + "Assume the previous slides shown in the index are already generated."
                + "The text of the slide must not start with \"This slide...\" or similar.";

        Optional<SlideText> slide = chatService.executeTemporary(chat, requestSlidePrompt, SlideText.class);
        return slide.get();
    }

    public SlideText generateSlideBullets(Chat chat, String slideTitle, String slidePrompt, int minWords,
            int maxWords) {

        String requestSlidePrompt = "Generate between 3 and 5 informative, condense, direct sentences "
                + "for a presentation slide, with the title \""
                + slideTitle + "\" about: \"" + slidePrompt + "\". In the text separate "
                + "each sentence by a newline. The total length of the text must be between "
                + minWords + " and " + maxWords + " words."
                + "Assume the previous slides shown in the index are already generated."
                + "The text of the slide must not start with \"This slide...\" or similar.";

        Optional<SlideText> slide = chatService.executeTemporary(chat, requestSlidePrompt, SlideText.class);
        return slide.get();

    }

    public Presentation generatePresentation(String presentationTitle, String presentationPrompt, int numSlides,
            int minWords, int maxWords, boolean bulletPoints, float imgWidth, float imgHeight,
            Presentation presentation) {

        // Presentation presentation = new Presentation();
        byte[] img = null;

        presentation.setTitle(presentationTitle);
        presentation.setDescriptionPrompt(presentationPrompt);

        // FIXME: Find another way of opt-out service image
        img = imageService.getImage(presentationPrompt, imgWidth, imgHeight).orElse(null);
        presentation.setFrontImg(img);

        String systemInitialization = "Your are an assistant that generates text for slides presentations about "
                + "the topic the user provides. Also, you must respond always in a JSON format";
        String API_KEY = env.getProperty("openai.api.key");
        Chat chat = new Chat(systemInitialization, API_KEY);

        IndexTransfer responseIndex = generateIndex(chat, presentationPrompt, numSlides);

        presentation.setIndex(
                new Index(responseIndex.getSlideTitles(), responseIndex.getSlideDescriptions()));

        List<Slide> slides = new ArrayList<Slide>();
        SlideText responseSlideText;

        for (int i = 0; i < numSlides; i++) {

            String slideTitle = presentation.getIndex().getSlideTitles().get(i);
            String slideDescription = presentation.getIndex().getSlideDescriptions().get(i);

            responseSlideText = generateSlide(chat, slideTitle, slideDescription, minWords,
                    maxWords, bulletPoints);

            String imagePrompt = "Inspirative beautiful photo about " + presentation.getTitle() + ", " + slideTitle;

            // FIXME: Find another way of opt-out service image
            img = imageService.getImage(imagePrompt, imgWidth, imgHeight).orElse(null);

            slides.add(new Slide(slideTitle, responseSlideText.getText(), (i + 1), img));
        }

        presentation.setSlides(slides);

        System.out.println("Total token usage: " + chat.getTotalUsage());

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
