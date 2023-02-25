package es.hackUDC.slAIds.model.services.ImageService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageServiceStableDiffusion implements ImageService {

    @Override
    public Optional<byte[]> getImage(String prompt, float width, float height) {
        StableDiffusionRequest stableDiffusionRequest = new StableDiffusionRequest(prompt, 1, 1, 20, width, height);
        try {
            StableDiffusionResponse stableDiffusionResponse = stableDiffusionRequest.executePostRequest(prompt);

            return Optional.of(stableDiffusionResponse.getImage());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}

record StableDiffusionRequest(String prompt, int batch_size, int n_iter, int steps, float width, float height) {

    private static final String STABLE_DIFFUSION_URL = "http://localhost:7860/sdapi/v1/txt2img";

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
