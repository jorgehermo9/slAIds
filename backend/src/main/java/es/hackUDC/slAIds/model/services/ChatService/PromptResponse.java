package es.hackUDC.slAIds.model.services.ChatService;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public record PromptResponse<T>(
        T response,
        String response_raw_text,
        String conversationId, String parentId) {

    record PromptResponseDto(String response, @JsonProperty("conversation_id") String conversationId,
            @JsonProperty("parent_id") String parentId) {

    }

    public static <T> Optional<PromptResponse<T>> parse(PromptResponseDto dto, String json_raw_text,
            Class<T> target_class) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T response = mapper.readValue(dto.response(), target_class);
            PromptResponse<T> promptResponse = new PromptResponse<T>(response, json_raw_text, dto.conversationId(),
                    dto.parentId());
            return Optional.of(promptResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}