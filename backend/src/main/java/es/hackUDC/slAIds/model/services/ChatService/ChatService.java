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
            String conversation_id, String parent_id) {

        PromptRequest<T> request = new PromptRequest<T>(prompt, target_class, conversation_id, parent_id);
        return request.execute();
    }
}
