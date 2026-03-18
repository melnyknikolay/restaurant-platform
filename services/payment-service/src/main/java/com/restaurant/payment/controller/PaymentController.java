
package com.restaurant.payment.controller;

import com.restaurant.payment.domain.Payment;
import com.restaurant.payment.saga.PaymentSagaService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentSagaService saga;

    public PaymentController(PaymentSagaService saga) {
        this.saga = saga;
    }

    @PostMapping("/{orderId}")
    public Payment pay(@PathVariable UUID orderId,
                       @RequestParam BigDecimal amount) {
        return saga.start(orderId, amount);
    }
}
