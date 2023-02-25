package es.hackUDC.slAIds.model.services.ImageService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageServiceBasic imageServiceBasic;

    @Autowired
    private ImageServiceStableDiffusion imageServiceStableDiffusion;

    @Autowired
    Environment environment;

    @Override
    public Optional<byte[]> getImage(String prompt, float width, float height) {
        if (stableDiffusionCondition()) {
            return imageServiceStableDiffusion.getImage(prompt, width, height);
        } else {
            return imageServiceBasic.getImage(prompt, width, height);
        }
    }

    private boolean stableDiffusionCondition() {
        String property = environment.getProperty("image.service");
        return property != null && property.equals("stableDiffusion");
    }

}
