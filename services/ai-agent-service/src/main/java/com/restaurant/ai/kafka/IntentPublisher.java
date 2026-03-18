
package com.restaurant.ai.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class IntentPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(Map<String, Object> intent) {
        kafkaTemplate.send("ai.intents", intent);
    }
}
