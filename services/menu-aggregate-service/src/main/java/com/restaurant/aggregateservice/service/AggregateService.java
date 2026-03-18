package com.restaurant.aggregateservice.service;

import reactor.core.publisher.Mono;
import com.restaurant.aggregateservice.dto.aggregate.MenuAggregate;
import com.restaurant.aggregateservice.dto.aggregate.MenuAggregateList;
import com.restaurant.aggregateservice.dto.aggregate.RatedMenuSort;
import com.restaurant.aggregateservice.dto.menu.Category;
import com.restaurant.aggregateservice.dto.review.ReviewSort;

public interface AggregateService {

    Mono<MenuAggregate> getMenuAggregateInfo(Long menuId, ReviewSort sort, int from, int size);

    Mono<MenuAggregateList> getMenusWithRatings(Category category, RatedMenuSort sort);
}
