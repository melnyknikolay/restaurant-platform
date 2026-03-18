package com.restaurant.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.restaurant.ordersservice.storage.model.MenuLineItem;
import com.restaurant.ordersservice.storage.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalPrice;
    private List<MenuLineItem> menuLineItems;
    private Address address;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
