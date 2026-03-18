package com.restaurant.aggregateservice.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import com.restaurant.aggregateservice.config.props.ExternalServiceProps;
import com.restaurant.aggregateservice.exception.MenuAggregate4xxException;
import com.restaurant.aggregateservice.exception.MenuAggregateException;

public abstract class BaseClient {

    protected final ExternalServiceProps props;

    protected BaseClient(ExternalServiceProps props) {
        this.props = props;
    }

    protected <T> Mono<T> applyTimeoutAndHandleExceptions(Mono<T> mono) {
        return mono
                .timeout(props.getDefaultTimeout())
                .onErrorMap(this::handleThrowable);
    }

    private Throwable handleThrowable(Throwable t) {
        if (t instanceof MenuAggregateException) {
            return t;
        }
        // In JDK 21 "Pattern Matching for Switch" (https://openjdk.org/jeps/441) no preview feature anymore
        if (t instanceof WebClientResponseException.NotFound) {
            return new MenuAggregate4xxException(t.getMessage(), HttpStatus.NOT_FOUND);
        } else if (t instanceof WebClientResponseException.BadRequest) {
            return new MenuAggregate4xxException(t.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new MenuAggregateException(t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
