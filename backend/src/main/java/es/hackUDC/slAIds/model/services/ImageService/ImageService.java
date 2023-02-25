package es.hackUDC.slAIds.model.services.ImageService;

import java.util.Optional;

public interface ImageService {

    public Optional<byte[]> getImage(String prompt);
}
