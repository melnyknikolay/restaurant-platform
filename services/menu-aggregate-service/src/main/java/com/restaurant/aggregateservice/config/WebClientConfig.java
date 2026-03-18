package com.restaurant.aggregateservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import com.restaurant.aggregateservice.config.props.ExternalServiceProps;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

    private final ExternalServiceProps props;

    @Bean
    public WebClient menuWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(props.getMenuServiceUrl())
                .build();
    }

    @Bean
    public WebClient reviewsWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(props.getReviewServiceUrl())
                .build();
    }
}
