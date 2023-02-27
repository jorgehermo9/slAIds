package es.hackUDC.slAIds.model.services.ChatService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatServiceConfig {
    @Value("${chat.api}")
    private String chatAPIHost;

    public String chatAPIHost() {
        return chatAPIHost;
    }
}