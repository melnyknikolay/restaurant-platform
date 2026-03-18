
package com.restaurant.notification.kafka;

import com.restaurant.notification.mapping.UserMappingService;
import com.restaurant.notification.service.NotificationService;
import com.restaurant.notification.template.TemplateService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService service;
    private final UserMappingService mapping;
    private final TemplateService templates;

    public NotificationConsumer(NotificationService service,
                                UserMappingService mapping,
                                TemplateService templates) {
        this.service = service;
        this.mapping = mapping;
        this.templates = templates;
    }

    @KafkaListener(topics = "payments.completed")
    public void handleCompleted(String orderId) {
        var userId = mapping.resolveUserId(orderId);
        service.notifyUser(userId, templates.orderReady(orderId));
    }

    @KafkaListener(topics = "payments.failed")
    public void handleFailed(String orderId) {
        throw new RuntimeException("simulate failure for DLQ");
    }
}
