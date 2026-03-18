
package com.restaurant.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VectorRepository {

    private final JdbcTemplate jdbc;

    public void save(UUID id, String content, float[] embedding) {
        jdbc.update("INSERT INTO menu_embeddings(id, content, embedding) VALUES (?, ?, ?)",
                id, content, embedding);
    }

    public List<String> search(float[] embedding) {
        return jdbc.queryForList(
                "SELECT content FROM menu_embeddings ORDER BY embedding <-> ?::vector LIMIT 5",
                String.class,
                (Object) embedding
        );
    }
}
