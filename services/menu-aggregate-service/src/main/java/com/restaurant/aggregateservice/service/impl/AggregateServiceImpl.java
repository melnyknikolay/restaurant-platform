package com.restaurant.aggregateservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.restaurant.aggregateservice.client.MenuClient;
import com.restaurant.aggregateservice.client.ReviewsClient;
import com.restaurant.aggregateservice.dto.aggregate.MenuAggregate;
import com.restaurant.aggregateservice.dto.aggregate.MenuAggregateList;
import com.restaurant.aggregateservice.dto.aggregate.RatedMenuSort;
import com.restaurant.aggregateservice.dto.menu.Category;
import com.restaurant.aggregateservice.dto.menu.MenuItem;
import com.restaurant.aggregateservice.dto.review.GetRatingsRequest;
import com.restaurant.aggregateservice.dto.review.MenuRatingInfo;
import com.restaurant.aggregateservice.dto.review.RatedReviewsList;
import com.restaurant.aggregateservice.dto.review.ReviewSort;
import com.restaurant.aggregateservice.mapper.MenuAggregateMapper;
import com.restaurant.aggregateservice.service.AggregateService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregateServiceImpl implements AggregateService {

    private final MenuClient menuClient;
    private final ReviewsClient reviewsClient;
    private final MenuAggregateMapper mapper;

    @Override
    public Mono<MenuAggregate> getMenuAggregateInfo(Long menuId, ReviewSort sort, int from, int size) {
        return Mono.zip(
                        values -> mapper.createMenuAggregate((MenuItem) values[0], (RatedReviewsList) values[1]),
                        menuClient.getMenuItem(menuId),
                        reviewsClient.getReviewsWithMenuRating(menuId, from, size, sort)
                )
                .doOnError(t -> log.error("Failed to getMenuAggregateInfo: {}", t.toString()));
    }

    @Override
    public Mono<MenuAggregateList> getMenusWithRatings(Category category, RatedMenuSort sort) {
        return menuClient.getMenusForCategory(category, sort)
                .flatMap(items -> getRatingsForMenuItems(items)
                        .map(ratingsMap -> mapper.createMenuAggregateList(ratingsMap, items, sort)))
                .doOnError(t -> log.error("Failed to getMenusWithRatings: {}", t.toString()));
    }

    private Mono<Map<Long, MenuRatingInfo>> getRatingsForMenuItems(List<MenuItem> items) {
        Set<Long> ids = items.stream().map(MenuItem::getId).collect(Collectors.toSet());
        return reviewsClient.getMenuRatings(new GetRatingsRequest(ids))
                .map(response -> response.getMenuRatings().stream()
                        .collect(Collectors.toMap(MenuRatingInfo::getMenuId, Function.identity())));
    }
}
