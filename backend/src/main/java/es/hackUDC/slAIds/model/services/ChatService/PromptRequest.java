package es.hackUDC.slAIds.model.services.ChatService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.hackUDC.slAIds.model.services.ChatService.PromptResponse.PromptResponseDto;

import java.util.Optional;

record PromptRequest<T>(String text, Class<T> target_class, String conversationId,
        String parentId) {

    public PromptRequest(String text, Class<T> target_class) {
        this(text, target_class, null, null);
    }

    public PromptRequest(String text, Class<T> target_class, String conversationId, String parentId) {
        this.text = text;
        this.target_class = target_class;
        this.conversationId = conversationId;
        this.parentId = parentId;
    }

    record PromptRequestDto(String text, @JsonProperty("conversation_id") String conversationId,
            @JsonProperty("parent_id") String parentId) {

        public <T> PromptRequestDto(PromptRequest<T> promptRequest) {
            this(promptRequest.text(), promptRequest.conversationId(), promptRequest.parentId());
        }
    }

    private static final String ChatAPIURL = "http://localhost:8000/chat";

    private static <T> Optional<PromptResponse<T>> executePostRequest(PromptRequest<T> request)
            throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        PromptRequestDto requestDto = new PromptRequestDto(request);
        String json = mapper.writeValueAsString(requestDto);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create(ChatAPIURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        PromptResponseDto responseDto = mapper.readValue(response.body(), PromptResponseDto.class);
        return PromptResponse.parse(responseDto, response.body(), request.target_class);
    }

    public Optional<PromptResponse<T>> execute() {
        try {
            return executePostRequest(this);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}