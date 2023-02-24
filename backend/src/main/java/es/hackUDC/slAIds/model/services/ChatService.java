package es.hackUDC.slAIds.model.services;

import java.net.http.HttpClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatService {
    private final String ChatAPIURL = "localhost:8000";

}

record PromptRequest(
        String text,
        @JsonProperty("conversation_id") String conversationId,
        @JsonProperty("parent_id") String parentId) {

    private String httpRequest(String json) {
        // Send request to ChatAPIURL, to the endpoint /chat
    }

    public PromptResponse executeRequest() {

        return new PromptResponse("response", "conversationId", "parentId");
    }
}

record PromptResponse(
        String response,
        @JsonProperty("conversation_id") String conversationId,
        @JsonProperty("parent_id") String parentId) {

}
