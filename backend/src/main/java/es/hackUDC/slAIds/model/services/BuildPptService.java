package es.hackUDC.slAIds.model.services;

import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.util.List;

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

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfGraphics2D;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class BuildPptService {

    public Presentation buildPpt(Presentation presentation) {

        XMLSlideShow ppt = new XMLSlideShow();
        XSLFTextShape titleShape;
        XSLFTextShape contentShape;

        XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);

        XSLFSlideLayout frontPageLayout = defaultMaster.getLayout(SlideLayout.TITLE_ONLY);
        XSLFSlide slide = ppt.createSlide(frontPageLayout);
        titleShape = slide.getPlaceholder(0);

        titleShape.setText(presentation.getTitle()).setFontSize(20.0);
        ;
        // Create index slide
        XSLFSlideLayout indexLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        slide = ppt.createSlide(indexLayout);
        titleShape = slide.getPlaceholder(0);
        contentShape = slide.getPlaceholder(1);
        titleShape.setText("Index").setFontSize(12.0);
        Index index = presentation.getIndex();
        StringBuilder indexText = new StringBuilder();
        for (String slideTitle : index.getSlideTitles()) {
            indexText.append(slideTitle).append("\n");
        }
        contentShape.setText(indexText.toString()).setFontSize(12.0);
        XSLFSlideLayout slidesLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        for (Slide modelSlide : presentation.getSlides()) {
            slide = ppt.createSlide(slidesLayout);
            titleShape = slide.getPlaceholder(0);
            contentShape = slide.getPlaceholder(1);
            titleShape.setText(modelSlide.getTitle()).setFontSize(20.0);
            contentShape.setText(modelSlide.getText()).setFontSize(12.0);
        }

        try {
            // Create a binary Data structure to store the pptx
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ppt.write(out);
            out.close();
            ppt.close();
            byte[] pptx = out.toByteArray();
            presentation.setPptx(pptx);

            // convert pptx to pdf

        } catch (Exception e) {

            e.printStackTrace();
            presentation.setError(true);
            presentation.setErrorMessage("Error while generating the presentation .pptx file");
            return presentation;
        }

        pptToPdf(presentation, ppt);

        return presentation;
    }

    public Presentation pptToPdf(Presentation presentation, XMLSlideShow ppt) {
        try {
            // getting the dimensions and size of the slide
            Dimension pgsize = ppt.getPageSize();
            List<XSLFSlide> slides = ppt.getSlides();

            // take first slide and draw it directly into PDF via awt.Graphics2D interface.
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
            document.setPageSize(new Rectangle((float) pgsize.getWidth(), (float) pgsize.getHeight()));
            document.open();

            for (XSLFSlide slide : slides) {
                PdfGraphics2D graphics = (PdfGraphics2D) pdfWriter.getDirectContent()
                        .createGraphics((float) pgsize.getWidth(), (float) pgsize.getHeight());
                slide.draw(graphics);
                graphics.dispose();
                document.newPage();
            }

            document.close();
            out.close();
            byte[] pdf = out.toByteArray();
            presentation.setPdf(pdf);

        } catch (Exception e) {
            e.printStackTrace();
            presentation.setError(true);
            presentation.setErrorMessage("Error while generating the presentation .pdf file");
        }

        return presentation;
    }
}
