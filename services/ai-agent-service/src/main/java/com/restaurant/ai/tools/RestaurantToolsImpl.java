
package com.restaurant.ai.tools;

import com.restaurant.ai.client.SearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantToolsImpl implements RestaurantTools {

    private final SearchClient searchClient;

    @Override
    public String searchMenu(String query) {
        try {
            return searchClient.searchRaw(query).block();
        } catch (Exception e) {
            return "[]";
        }
    }

    @Override
    public String recommend() {
        return "Recommended: Pizza Margherita, Spicy Ramen";
    }

    @Override
    public String createOrder(String dishName) {
        String orderId = UUID.randomUUID().toString();
        return "Order created for " + dishName + ". OrderId=" + orderId;
    }

    @Override
    public String getOrderStatus(String orderId) {
        return "Order " + orderId + " is in progress";
    }
}
