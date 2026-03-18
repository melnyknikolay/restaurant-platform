package com.restaurant.aggregateservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.restaurant.aggregateservice.BaseTest;
import com.restaurant.aggregateservice.dto.aggregate.MenuAggregate;
import com.restaurant.aggregateservice.dto.aggregate.MenuAggregateList;
import com.restaurant.aggregateservice.dto.aggregate.RatedMenuItem;
import com.restaurant.aggregateservice.dto.aggregate.RatedMenuSort;
import com.restaurant.aggregateservice.dto.menu.Category;
import com.restaurant.aggregateservice.dto.review.ReviewSort;
import com.restaurant.aggregateservice.exception.MenuAggregateException;

import java.util.Comparator;

import static com.restaurant.aggregateservice.testutil.TestConstants.MENU_ONE_ID;
import static com.restaurant.aggregateservice.testutil.TestDateProvider.*;

class AggregateServiceImplTest extends BaseTest {

    @Autowired
    private AggregateServiceImpl aggregateService;

    @Test
    void getMenuAggregateInfo_returnsCorrectResponse() {
        stubForCorrectRatedReviewsList();
        stubForCorrectMenuItemResponse();

        StepVerifier.create(getMenuAggregateMono())
                .expectNextMatches(response ->
                        response.equals(expectedMenuAggregate()))
                .verifyComplete();
    }

    @Test
    void getMenuAggregateInfo_returnsErrorWhenMenuServiceUnavailable() {
        stubForCorrectRatedReviewsList();
        stubForMenuItem500Error();

        StepVerifier.create(getMenuAggregateMono())
                .expectError(MenuAggregateException.class)
                .verify();
    }

    @Test
    void getMenuAggregateInfo_returnsFallbackWhenReviewServiceUnavailable() {
        stubForRatedReviewsList500Error();
        stubForCorrectMenuItemResponse();

        StepVerifier.create(getMenuAggregateMono())
                .expectNextMatches(response ->
                        response.equals(expectedMenuAggregateWithFallback()))
                .verifyComplete();
    }

    @Test
    void getMenuAggregateInfo_returnsErrorWhenMenuServiceTimedOut() {
        stubForCorrectRatedReviewsList();
        stubForMenuItemTimeout();

        StepVerifier.create(getMenuAggregateMono())
                .expectError(MenuAggregateException.class)
                .verify();
    }

    @Test
    void getMenuAggregateInfo_returnsFallbackWhenReviewServiceTimedOut() {
        stubForRatedReviewsListTimeout();
        stubForCorrectMenuItemResponse();

        StepVerifier.create(getMenuAggregateMono())
                .expectNextMatches(response ->
                        response.equals(expectedMenuAggregateWithFallback()))
                .verifyComplete();
    }

    @Test
    void getMenusWithRatings_returnsCorrectResponse() {
        stubForCorrectMenuListResponse();
        stubForCorrectMenuRatingsResponse();

        StepVerifier.create(getMenuRatingsMono())
                .expectNextMatches(response ->
                        response.equals(expectedMenuAggregateList(Comparator.comparing(RatedMenuItem::getCreatedAt).reversed())))
                .verifyComplete();
    }

    @Test
    void getMenusWithRatings_returnsErrorWhenMenuServiceUnavailable() {
        stubForMenuItemList500Error();
        stubForCorrectMenuRatingsResponse();

        StepVerifier.create(getMenuRatingsMono())
                .expectError(MenuAggregateException.class)
                .verify();
    }

    @Test
    void getMenusWithRatings_returnsErrorWhenReviewServiceUnavailable() {
        stubForCorrectMenuListResponse();
        stubForMenuRatings500Error();

        StepVerifier.create(getMenuRatingsMono())
                .expectError(MenuAggregateException.class)
                .verify();
    }

    @Test
    void getMenusWithRatings_returnsErrorWhenMenuServiceTimedOut() {
        stubForMenuItemListTimeout();
        stubForCorrectMenuRatingsResponse();

        StepVerifier.create(getMenuRatingsMono())
                .expectError(MenuAggregateException.class)
                .verify();
    }

    @Test
    void getMenusWithRatings_returnsErrorWhenReviewServiceTimedOut() {
        stubForCorrectMenuListResponse();
        stubForMenuRatingsTimeout();

        StepVerifier.create(getMenuRatingsMono())
                .expectError(MenuAggregateException.class)
                .verify();
    }

    private Mono<MenuAggregate> getMenuAggregateMono() {
        return aggregateService.getMenuAggregateInfo(MENU_ONE_ID, ReviewSort.DATE_ASC, 0, 10);
    }

    private Mono<MenuAggregateList> getMenuRatingsMono() {
        return aggregateService.getMenusWithRatings(Category.DRINKS, RatedMenuSort.DATE_DESC);
    }
}