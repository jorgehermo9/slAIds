package es.hackUDC.slAIds.model.services.ImageService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ImageServiceStableDiffusion implements ImageService {

    @Override
    public Optional<byte[]> getImage(String prompt) {
        return null;
    }

}
