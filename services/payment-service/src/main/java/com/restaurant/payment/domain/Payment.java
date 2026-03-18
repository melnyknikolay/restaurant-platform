
package com.restaurant.payment.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Payment {

    @Id
    private UUID id;

    private UUID orderId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Instant createdAt;

    public Payment() {}

    public Payment(UUID id, UUID orderId, BigDecimal amount) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.CREATED;
        this.createdAt = Instant.now();
    }

    public void processing() { this.status = PaymentStatus.PROCESSING; }
    public void complete() { this.status = PaymentStatus.COMPLETED; }
    public void fail() { this.status = PaymentStatus.FAILED; }
    public void cancel() { this.status = PaymentStatus.CANCELLED; }

    public UUID getOrderId() { return orderId; }
}
