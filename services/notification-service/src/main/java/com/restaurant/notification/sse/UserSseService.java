
package com.restaurant.notification.sse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserSseService {

    private final Map<UUID, Sinks.Many<String>> sinks = new ConcurrentHashMap<>();

    public Flux<String> stream(UUID userId) {
        return sinks.computeIfAbsent(userId,
                id -> Sinks.many().multicast().onBackpressureBuffer()
        ).asFlux();
    }

    public void send(UUID userId, String message) {
        sinks.computeIfAbsent(userId,
                id -> Sinks.many().multicast().onBackpressureBuffer()
        ).tryEmitNext(message);
    }
}
