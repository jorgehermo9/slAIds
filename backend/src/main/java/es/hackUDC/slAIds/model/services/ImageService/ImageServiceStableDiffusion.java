package es.hackUDC.slAIds.model.services.ImageService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

>>>>>>> Stashed changes
@Service
public class ImageServiceStableDiffusion implements ImageService {

    @Override
    public Optional<byte[]> getImage(String prompt) {
        StableDiffusionRequest stableDiffusionRequest = new StableDiffusionRequest(prompt, 1, 1, 20, 720, 512);
        try {
            StableDiffusionResponse stableDiffusionResponse = stableDiffusionRequest.executePostRequest(prompt);

            return Optional.of(stableDiffusionResponse.getImage());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}

record StableDiffusionRequest(String prompt, int batch_size, int n_iter, int steps, int width, int height) {

    private static final String STABLE_DIFFUSION_URL = "http://172.20.10.6:7860/sdapi/v1/txt2img";

    public StableDiffusionResponse executePostRequest(String prompt)
            throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(this);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create(STABLE_DIFFUSION_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(json);
        System.out.println(response.body());

        StableDiffusionResponse stableDiffusionResponse = mapper.readValue(response.body(),
                StableDiffusionResponse.class);

        return stableDiffusionResponse;
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
record StableDiffusionResponse(List<String> images) {

    public byte[] getImage() {
        // get first element of images array and decode base64 string to byte array
        String base64Image = images.get(0);
        return java.util.Base64.getDecoder().decode(base64Image);
    }
}