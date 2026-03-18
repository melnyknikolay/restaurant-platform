
package com.restaurant.notification.template;

import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public String orderReady(String orderId) {
        return "Order #" + orderId + " is ready";
    }
}
