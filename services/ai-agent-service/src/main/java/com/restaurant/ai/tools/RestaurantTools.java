
package com.restaurant.ai.tools;

import dev.langchain4j.agent.tool.Tool;

public interface RestaurantTools {

    @Tool("Search dishes in the menu")
    String searchMenu(String query);

    @Tool("Recommend dishes for the user")
    String recommend();

    @Tool("Create a new order")
    String createOrder(String dishName);

    @Tool("Get order status")
    String getOrderStatus(String orderId);
}
