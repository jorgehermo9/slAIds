package es.hackUDC.slAIds.model.services.ImageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageServiceConfig {
    @Value("${image.api}")
    private String imageAPIHost;

    public String imageAPIHost() {
        return imageAPIHost;
    }
}
