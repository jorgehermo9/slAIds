package es.hackUDC.slAIds.model.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.qos.logback.core.net.server.Client;

public class ChatService {
    private final String ChatAPIURL = "localhost:8000";

    public static String execute() {
        PromptRequest request = new PromptRequest("text", "conversationId", "parentId");
        PromptResponse response = request.executeRequest();
        return response.response();
    }
}

record PromptRequest(
        String text,
        @JsonProperty("conversation_id") String conversationId,
        @JsonProperty("parent_id") String parentId) {

    private static final String ChatAPIURL = null;

    private String httpRequest(String json) throws IOException, InterruptedException {
        // Send request to ChatAPIURL, to the endpoint /chat, with the json string as
        // body

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ChatAPIURL + "/chat"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // send request

        HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
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
