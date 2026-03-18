package com.restaurant.ordersservice.mapper;

import org.springframework.stereotype.Component;
import com.restaurant.ordersservice.storage.model.MenuOrder;
import com.restaurant.ordersservice.storage.model.OrderPlacedEvent;

@Component
public class OrderOutboxMapper {

    public OrderPlacedEvent toOrderOutbox(MenuOrder order) {
        return OrderPlacedEvent.builder()
                .orderId(order.getId())
                .createdBy(order.getCreatedBy())
                .city(order.getCity())
                .street(order.getStreet())
                .house(order.getApartment())
                .apartment(order.getApartment())
                .createdAt(order.getCreatedAt())
                .build();
    }
}