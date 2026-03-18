
package com.restaurant.ai.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class StreamingService {

    public Flux<String> stream(String text) {
        String[] tokens = text.split(" ");
        return Flux.fromArray(tokens)
                .delayElements(Duration.ofMillis(80))
                .map(t -> t + " ");
    }
}
