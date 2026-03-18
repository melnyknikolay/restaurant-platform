package com.restaurant.ordersservice.storage.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.restaurant.ordersservice.storage.model.OrderPlacedEvent;

public interface OrderPlacedEventRepository extends ReactiveCrudRepository<OrderPlacedEvent, Long> {
}