
package com.restaurant.notification.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Notification {

    @Id
    private UUID id;
    private UUID userId;
    private String message;
    private boolean read;
    private Instant createdAt;

    public Notification() {}

    public Notification(UUID userId, String message) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.message = message;
        this.read = false;
        this.createdAt = Instant.now();
    }

    public UUID getUserId() { return userId; }
    public String getMessage() { return message; }
}
