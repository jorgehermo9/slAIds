package es.hackUDC.slAIds.model.services.ChatService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public <T> Optional<PromptResponse<T>> execute(String prompt, Class<T> targetClass) {

        PromptRequest<T> request = new PromptRequest<T>(prompt, targetClass);
        return request.execute();
    }

    public <T, U> Optional<PromptResponse<T>> executeWithConversation(String prompt, Class<T> targetClass,
            String conversationId, String parentId) {

        PromptRequest<T> request = new PromptRequest<T>(prompt, targetClass, conversationId, parentId);
        return request.execute();
    }
}
