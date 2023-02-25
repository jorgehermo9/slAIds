package es.hackUDC.slAIds.model.services.ImageService;

import java.io.FileInputStream;
import java.util.Optional;

public class ImageServiceBasic implements ImageService {

    private final String IMAGE_PATH = "src/main/resources/static/images/no_image.png";

    @Override
    public Optional<byte[]> getImage(String prompt) {
        // READ IMAGE FROM FILE

        try {
            FileInputStream fileInputStream = new FileInputStream(IMAGE_PATH);
            byte[] image = fileInputStream.readAllBytes();
            fileInputStream.close();
            return Optional.of(image);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
