package com.restaurant.aggregateservice.client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.restaurant.aggregateservice.config.props.ExternalServiceProps;
import com.restaurant.aggregateservice.dto.review.GetRatingsRequest;
import com.restaurant.aggregateservice.dto.review.RatedReviewsList;
import com.restaurant.aggregateservice.dto.review.RatingsList;
import com.restaurant.aggregateservice.dto.review.ReviewSort;
import com.restaurant.aggregateservice.exception.MenuAggregateException;

@Component
public class ReviewsClient extends BaseClient {

    private static final String REVIEW_BACKEND = "reviewBackend";

    private final WebClient webClient;

    public ReviewsClient(@Qualifier("reviewsWebClient") WebClient reviewsWebClient,
                         ExternalServiceProps props) {
        super(props);
        this.webClient = reviewsWebClient;
    }

    @CircuitBreaker(name = REVIEW_BACKEND, fallbackMethod = "getReviewsWithMenuRatingCBFallback")
    @Retry(name = REVIEW_BACKEND, fallbackMethod = "getReviewsWithMenuRatingRetryFallback")
    public Mono<RatedReviewsList> getReviewsWithMenuRating(Long menuId, int from, int size, ReviewSort sort) {
        var mono = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(props.getMenuReviewsPath())
                        .path("/{menuId}")
                        .queryParam("from", from)
                        .queryParam("size", size)
                        .queryParam("sortBy", sort)
                        .build(menuId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new MenuAggregateException("Review Service unavailable", HttpStatus.SERVICE_UNAVAILABLE)))
                .bodyToMono(RatedReviewsList.class);

        return applyTimeoutAndHandleExceptions(mono);
    }

    @CircuitBreaker(name = REVIEW_BACKEND)
    @Retry(name = REVIEW_BACKEND)
    public Mono<RatingsList> getMenuRatings(GetRatingsRequest request) {
        var mono = webClient
                .post()
                .uri(props.getMenuRatingsPath())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new MenuAggregateException("Review Service unavailable", HttpStatus.SERVICE_UNAVAILABLE)))
                .bodyToMono(RatingsList.class);

        return applyTimeoutAndHandleExceptions(mono);
    }

    private Mono<RatedReviewsList> getReviewsWithMenuRatingCBFallback(Long menuId, int from, int size, ReviewSort sort, CallNotPermittedException e) {
        return Mono.just(RatedReviewsList.fallbackResponse(menuId));
    }

    private Mono<RatedReviewsList> getReviewsWithMenuRatingRetryFallback(Long menuId, int from, int size, ReviewSort sort, MenuAggregateException e) {
        return Mono.just(RatedReviewsList.fallbackResponse(menuId));
    }
}