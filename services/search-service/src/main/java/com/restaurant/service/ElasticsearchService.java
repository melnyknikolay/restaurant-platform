
package com.restaurant.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.restaurant.dto.MenuDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElasticsearchService {

    private final ElasticsearchClient client;

    @Value("${elasticsearch.index}")
    private String index;

    public List<Object> search(String query) {
        try {
            var response = client.search(s -> s
                            .index(index)
                            .query(q -> q.multiMatch(m -> m
                                    .fields("name", "description")
                                    .query(query)
                            )),
                    Object.class);

            return response.hits().hits().stream().map(Hit::source).toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void index(MenuDocument doc) {
        try {
            client.index(i -> i
                    .index(index)
                    .id(doc.id())
                    .document(doc)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to index document", e);
        }
    }
}
