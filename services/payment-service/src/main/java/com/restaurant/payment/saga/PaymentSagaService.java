
package com.restaurant.payment.saga;

import com.restaurant.payment.domain.*;
import com.restaurant.payment.outbox.*;
import com.restaurant.payment.paypal.PayPalClient;
import com.restaurant.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentSagaService {

    private final PaymentRepository repo;
    private final PayPalClient payPal;
    private final OutboxRepository outbox;

    public PaymentSagaService(PaymentRepository repo,
                              PayPalClient payPal,
                              OutboxRepository outbox) {
        this.repo = repo;
        this.payPal = payPal;
        this.outbox = outbox;
    }

    @Transactional
    public Payment start(UUID orderId, BigDecimal amount) {

        Payment payment = new Payment(UUID.randomUUID(), orderId, amount);
        payment.processing();
        repo.save(payment);

        try {
            payPal.createPayment(amount);

            payment.complete();

            outbox.save(new OutboxEvent(
                "payments.completed",
                orderId.toString(),
                orderId.toString()
            ));

        } catch (Exception e) {

            payment.fail();

            outbox.save(new OutboxEvent(
                "payments.failed",
                orderId.toString(),
                orderId.toString()
            ));

            // compensation
            outbox.save(new OutboxEvent(
                "orders.cancel",
                orderId.toString(),
                orderId.toString()
            ));
        }

        return repo.save(payment);
    }
}
