package es.hackUDC.slAIds.model.services;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.stereotype.Service;

import es.hackUDC.slAIds.model.entities.Index;
import es.hackUDC.slAIds.model.entities.Presentation;
import es.hackUDC.slAIds.model.entities.Slide;

@Service
public class BuildPptService {

    public static void buildPpt(Presentation presentation) {

        System.out.println("Building PPT");
        XMLSlideShow ppt = new XMLSlideShow();
        XSLFTextShape titleShape;
        XSLFTextShape contentShape;

        XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);

        XSLFSlideLayout frontPageLayout = defaultMaster.getLayout(SlideLayout.TITLE_ONLY);
        XSLFSlide slide = ppt.createSlide(frontPageLayout);
        titleShape = slide.getPlaceholder(0);

        titleShape.setText(presentation.getTitle()).setFontSize(20.0);
        ;
        System.out.println("First Slide");

        // Create index slide
        XSLFSlideLayout indexLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        System.out.println("1");
        slide = ppt.createSlide(indexLayout);
        System.out.println("2");
        titleShape = slide.getPlaceholder(0);
        System.out.println("3");
        contentShape = slide.getPlaceholder(1);
        System.out.println("4");
        titleShape.setText("Index").setFontSize(12.0);
        System.out.println("5");
        Index index = presentation.getIndex();
        System.out.println("6");
        String indexText = "";
        System.out.println("7");
        for (String slideTitle : index.getSlideTitles()) {
            System.out.println("8");
            // Create an index using bullet points
            indexText += "- " + slideTitle + "\n";
        }
        System.out.println("9");
        contentShape.setText(indexText).setFontSize(12.0);
        System.out.println("10");
        XSLFSlideLayout slidesLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        int i = 0;
        for (Slide modelSlide : presentation.getSlides()) {
            System.out.println("Slide " + i++);
            slide = ppt.createSlide(slidesLayout);
            titleShape = slide.getPlaceholder(0);
            contentShape = slide.getPlaceholder(1);
            titleShape.setText(modelSlide.getTitle()).setFontSize(20.0);
            contentShape.setText(modelSlide.getText()).setFontSize(12.0);
        }

        try {
            System.out.println("closing ppt");
            ppt.write(new java.io.FileOutputStream("test2.pptx"));
            ppt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
