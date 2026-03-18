package com.restaurant.ordersservice.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.restaurant.ordersservice.dto.CreateOrderRequest;
import com.restaurant.ordersservice.dto.OrderResponse;
import com.restaurant.ordersservice.dto.SortBy;

public interface MenuOrderService {

    Mono<OrderResponse> createOrder(CreateOrderRequest request, String username);

    Flux<OrderResponse> getOrdersOfUser(String username, SortBy sortBy, int from, int size);
}
