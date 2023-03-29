package es.hackUDC.slAIds.model.services.ChatService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Chat {

    private List<Message> message_history;
    private String API_KEY;
    private double totalUsage;

    public Chat(String systemInitialization, String API_KEY) {
        message_history = new ArrayList<Message>();
        message_history.add(new Message("system", systemInitialization));
        this.API_KEY = API_KEY;
    }

    public String processPrompt(String prompt) {
        Message assistantResponse = doProcessPrompt(prompt);
        return assistantResponse.content();
    }

    public String processTemporaryPrompt(String prompt) {
        Message assistantResponse = doProcessPrompt(prompt);
        // Remove the last two messages from the history
        message_history.remove(message_history.size() - 1);
        message_history.remove(message_history.size() - 1);
        return assistantResponse.content();
    }

    private Message doProcessPrompt(String prompt) {
        message_history.add(new Message("user", prompt));
        System.out.println("User: " + prompt);
        ChatGPTRequest request = new ChatGPTRequest("gpt-3.5-turbo", message_history, 0.7f);
        ChatGPTResponse response = getResponse(request);
        Message assistantResponse = response.choices().get(0).message();
        System.out.println("Assistant: " + assistantResponse.content());
        System.out.println("Usage: " + response.usage().totalTokens() + " tokens");
        totalUsage += response.usage().totalTokens();
        message_history.add(assistantResponse);
        return assistantResponse;
    }

    private ChatGPTResponse getResponse(ChatGPTRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);

        // FIXME: Check if the finish_reason is "stop" or "length",
        // and continue asking for more responses if it is not "stop"
        return restTemplate.postForObject("https://api.openai.com/v1/chat/completions",
                entity, ChatGPTResponse.class);
    }

    public double getTotalUsage() {
        return totalUsage;
    }

    public float getTotalCost() {
        // Hardcoded price of 0.002 per 1k tokens (https://openai.com/pricing)
        return (float) (totalUsage / 1000 * 0.002);
    }
}

record Message(
        String role,
        String content) {
}

record ChatGPTRequest(
        String model,
        List<Message> messages,
        float temperature) {
}

record ChatGPTResponse(
        String id,
        String object,
        Long created,
        String model,
        List<ChatGPTResponseChoice> choices,
        ChatGPTUsage usage) {
}

record ChatGPTResponseChoice(
        Message message,
        Double index,
        String finish_reason) {
}

record ChatGPTUsage(
        @JsonProperty("prompt_tokens") int promptTokens,
        @JsonProperty("completion_tokens") int completionTokens,
        @JsonProperty("total_tokens") int totalTokens) {
}