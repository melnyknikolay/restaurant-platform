
package com.restaurant.service;

import com.restaurant.repository.VectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingService embeddingService;
    private final VectorRepository repository;

    public List<String> search(String query) {
        float[] embedding = embeddingService.embed(query);
        return repository.search(embedding);
    }
}
