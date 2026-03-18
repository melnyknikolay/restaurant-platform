package com.restaurant.ordersservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import com.restaurant.ordersservice.config.props.OrderServiceProps;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

    private final OrderServiceProps props;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(props.getMenuServiceUrl())
                .build();
    }
}