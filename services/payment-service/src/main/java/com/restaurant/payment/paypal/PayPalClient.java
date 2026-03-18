
package com.restaurant.payment.paypal;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class PayPalClient {

    private final WebClient webClient;

    public PayPalClient(WebClient.Builder builder,
                        @org.springframework.beans.factory.annotation.Value("${paypal.base-url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    @Retry(name = "paypal")
    @CircuitBreaker(name = "paypal", fallbackMethod = "fallback")
    public String createPayment(BigDecimal amount) {
        return webClient.post()
                .uri("/v2/checkout/orders")
                .bodyValue("{'intent':'CAPTURE'}")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String fallback(BigDecimal amount, Throwable t) {
        return "fallback-payment";
    }
}
