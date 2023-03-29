package es.hackUDC.slAIds.model.services.ChatService;

import java.lang.reflect.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

record PromptRequest<T>(String promptText, Class<T> targetClass) {

    private static <T> String generateJsonPromptText(Class<T> targetClass) {
        Field[] fields = targetClass.getDeclaredFields();
        String basePrompt = "Your response should be only in JSON format and should have the following " +
                fields.length + " key(s): ";

        StringBuilder prompt = new StringBuilder(basePrompt);
        if (fields.length > 0) {
            for (Field field : fields) {
                prompt.append(field.getName());
                prompt.append(", ");
            }
            prompt.delete(prompt.length() - 2, prompt.length());
            prompt.append(". ");
        }

        String endPrompt = "Do not respond anything more than JSON";
        prompt.append(endPrompt);
        return prompt.toString();

    }

    public Optional<T> execute(Chat chat) {
        try {
            String promptTextWithJson = promptText + "\n" + generateJsonPromptText(targetClass);
            String response = chat.processPrompt(promptTextWithJson);
            T targetObject = new ObjectMapper().readValue(response, targetClass);
            return Optional.of(targetObject);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<T> executeTemporary(Chat chat) {
        try {
            String promptTextWithJson = promptText + "\n" + generateJsonPromptText(targetClass);
            String response = chat.processTemporaryPrompt(promptTextWithJson);
            T targetObject = new ObjectMapper().readValue(response, targetClass);
            return Optional.of(targetObject);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}