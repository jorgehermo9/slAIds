package es.hackUDC.slAIds.model.services.ChatService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.hackUDC.slAIds.model.services.ChatService.PromptResponse.PromptResponseDto;

import java.util.Optional;

record PromptRequest<T>(String promptText, Class<T> target_class, String conversationId,
        String parentId) {

    public PromptRequest(String promptText, Class<T> target_class) {
        this(promptText, target_class, null, null);
    }

    public PromptRequest(String promptText, Class<T> target_class, String conversationId, String parentId) {
        String finalPromptText = promptText + "\n" + generateJsonPromptText(target_class);
        this.promptText = finalPromptText;
        this.target_class = target_class;
        this.conversationId = conversationId;
        this.parentId = parentId;
    }

    private static <T> String generateJsonPromptText(Class<T> target_class) {
        Field[] fields = target_class.getDeclaredFields();
        String basePrompt = "Your response should be only in JSON format and should have the following keys " +
                fields.length + " : ";

        String fieldsPrompt = "";
        if (fields.length > 0) {
            StringBuilder sb = new StringBuilder(basePrompt);
            for (Field field : fields) {
                sb.append(field.getName());
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append(".");
            fieldsPrompt = sb.toString();
        }

        String endPrompt = "Do not respond anything more than JSON";

        return basePrompt + fieldsPrompt + endPrompt;

    }

    record PromptRequestDto(String text, @JsonProperty("conversation_id") String conversationId,
            @JsonProperty("parent_id") String parentId) {

        public <T> PromptRequestDto(PromptRequest<T> promptRequest) {
            this(promptRequest.promptText(), promptRequest.conversationId(), promptRequest.parentId());
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
        
        System.out.println(json);
        System.out.println(response.body());
        
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