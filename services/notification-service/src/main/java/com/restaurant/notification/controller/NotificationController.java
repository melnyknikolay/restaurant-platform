
package com.restaurant.notification.controller;

import com.restaurant.notification.sse.UserSseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final UserSseService sse;

    public NotificationController(UserSseService sse) {
        this.sse = sse;
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam UUID userId) {
        return sse.stream(userId);
    }
}
