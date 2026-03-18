
package com.restaurant.kafka;

import com.restaurant.dto.MenuDocument;
import com.restaurant.repository.VectorRepository;
import com.restaurant.service.ElasticsearchService;
import com.restaurant.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuEventConsumer {

    private final VectorRepository repository;
    private final EmbeddingService embeddingService;
    private final ElasticsearchService elasticsearchService;

    @KafkaListener(topics = "menu.updated")
    public void consume(Map<String, Object> event) {

        String id = (String) event.get("id");
        String name = (String) event.get("name");
        String description = (String) event.get("description");

        log.info("Indexing menu item: id={}, name={}", id, name);

        String text = (name != null ? name : "") + " " +
                (description != null ? description : "");

        float[] embedding = embeddingService.embed(text);

        repository.save(UUID.fromString(id), text, embedding);

        // 2. Elasticsearch
        elasticsearchService.index(
                new MenuDocument(id, name, description)
        );

    }

    @KafkaListener(topics = "menu.updated.DLT")
    public void consumeDlq(Map<String, Object> event) {
        log.error("DLQ event: {}", event);
    }
}
