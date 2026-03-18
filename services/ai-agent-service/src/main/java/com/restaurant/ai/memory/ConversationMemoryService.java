
package com.restaurant.ai.memory;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationMemoryService {

    private final Map<String, Deque<String>> memory = new ConcurrentHashMap<>();
    private final int maxMessages = 20;

    public void add(String sessionId, String message) {
        memory.computeIfAbsent(sessionId, k -> new ArrayDeque<>());
        Deque<String> deque = memory.get(sessionId);
        if (deque.size() >= maxMessages) {
            deque.pollFirst();
        }
        deque.addLast(message);
    }

    public List<String> history(String sessionId) {
        return new ArrayList<>(memory.getOrDefault(sessionId, new ArrayDeque<>()));
    }

    public void clear(String sessionId) {
        memory.remove(sessionId);
    }
}
