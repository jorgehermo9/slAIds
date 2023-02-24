package es.hackUDC.slAIds.model.services.ChatService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public <T> Optional<PromptResponse<T>> execute(String prompt, Class<T> target_class) {

        PromptRequest<T> request = new PromptRequest<T>(prompt, target_class);
        return request.execute();
    }

    public <T, U> Optional<PromptResponse<T>> execute_with_conversation(String prompt, Class<T> target_class,
            PromptResponse<U> previous_response) {

        PromptRequest<T> request = new PromptRequest<T>(prompt, target_class, previous_response.conversationId(),
                previous_response.parentId());
        return request.execute();
    }
}
