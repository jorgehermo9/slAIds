package es.hackUDC.slAIds.model.services.ImageService;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ImageServiceBasic implements ImageService {

    @Override
    public Optional<byte[]> getImage(String prompt, float width, float height) {
        // READ IMAGE FROM FILE
        try {
            // Get image from resources no_image.png
            InputStream ioStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("static/images/no_image.png");
            byte[] image = ioStream.readAllBytes();
            ioStream.close();
            return Optional.of(image);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
