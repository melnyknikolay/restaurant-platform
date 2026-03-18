
package com.restaurant.payment.outbox;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_event")
public class OutboxEvent {

    @Id
    private UUID id;

    private String aggregateType;
    private String aggregateId;
    private String type;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Instant createdAt;

    public OutboxEvent() {}

    public OutboxEvent(String type, String payload, String aggregateId) {
        this.id = UUID.randomUUID();
        this.aggregateType = "payment";
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
        this.createdAt = Instant.now();
    }
}
