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
        slide = ppt.createSlide(indexLayout);
        titleShape = slide.getPlaceholder(0);
        contentShape = slide.getPlaceholder(1);
        titleShape.setText("Index").setFontSize(12.0);
        Index index = presentation.getIndex();
        String indexText = "";
        for (String slideTitle : index.getSlideTitles()) {
            // Create an index using bullet points
            indexText += "- " + slideTitle + "\n";
        }
        contentShape.setText(indexText).setFontSize(12.0);

        XSLFSlideLayout slidesLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        for (Slide modelSlide : presentation.getSlides()) {
            slide = ppt.createSlide(slidesLayout);
            titleShape = slide.getPlaceholder(0);
            contentShape = slide.getPlaceholder(1);
            titleShape.setText(modelSlide.getTitle()).setFontSize(20.0);
            contentShape.setText(modelSlide.getText()).setFontSize(12.0);
        }

        try {
            ppt.write(new java.io.FileOutputStream("test.pptx"));
            ppt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
