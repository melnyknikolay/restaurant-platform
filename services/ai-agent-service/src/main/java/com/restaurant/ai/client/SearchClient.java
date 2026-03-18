
package com.restaurant.ai.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SearchClient {

    private final WebClient webClient;

    public SearchClient(@Value("${search.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<String> searchRaw(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search").queryParam("q", query).build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("[]"));
    }
}
