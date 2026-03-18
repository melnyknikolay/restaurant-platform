
package com.restaurant.ai.service;

import com.restaurant.ai.memory.ConversationMemoryService;
import com.restaurant.ai.tools.RestaurantTools;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiAgentService {

    private final ConversationMemoryService memory;
    private final Assistant assistant;

    public AiAgentService(
            ConversationMemoryService memory,
            RestaurantTools tools,
            @Value("${ai.openai.api-key}") String apiKey,
            @Value("${ai.model}") String modelName
    ) {
        this.memory = memory;

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();

        this.assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(tools)
                .build();
    }

    public String chat(String sessionId, String message) {

        List<String> history = memory.history(sessionId);

        String prompt = buildPrompt(history, message);

        String response = assistant.chat(prompt);

        memory.add(sessionId, "User: " + message);
        memory.add(sessionId, "AI: " + response);

        return response;
    }

    private String buildPrompt(List<String> history, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are a helpful restaurant assistant.\n");
        if (!history.isEmpty()) {
            sb.append("Conversation history:\n");
            history.forEach(h -> sb.append(h).append("\n"));
        }
        sb.append("User: ").append(message).append("\n");
        sb.append("Answer:");
        return sb.toString();
    }

    public interface Assistant {
        String chat(String message);
    }
}
