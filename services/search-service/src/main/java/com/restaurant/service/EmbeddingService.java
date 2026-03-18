
package com.restaurant.service;

import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final OpenAiEmbeddingModel model;

    public EmbeddingService() {
        this.model = OpenAiEmbeddingModel.withApiKey(System.getenv("OPENAI_API_KEY"));
    }

    public float[] embed(String text) {
        return model.embed(text).content().vector();
    }
}
