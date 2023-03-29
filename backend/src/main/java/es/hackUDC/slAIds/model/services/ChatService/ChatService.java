package es.hackUDC.slAIds.model.services.ChatService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public <T> Optional<T> execute(Chat chat, String prompt, Class<T> targetClass) {

        PromptRequest<T> request = new PromptRequest<T>(prompt, targetClass);
        return request.execute(chat);
    }
}
