
package com.restaurant.ai.controller;

import com.restaurant.ai.dto.ChatRequest;
import com.restaurant.ai.dto.ChatResponse;
import com.restaurant.ai.service.AiAgentService;
import com.restaurant.ai.service.StreamingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiAgentService agentService;
    private final StreamingService streamingService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody @Valid ChatRequest request) {
        String answer = agentService.chat(request.sessionId(), request.message());
        return new ChatResponse(answer);
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(
            @RequestParam String sessionId,
            @RequestParam String message
    ) {
        String answer = agentService.chat(sessionId, message);
        return streamingService.stream(answer);
    }
}
