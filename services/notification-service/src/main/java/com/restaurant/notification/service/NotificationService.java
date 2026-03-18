
package com.restaurant.notification.service;

import com.restaurant.notification.domain.*;
import com.restaurant.notification.repository.*;
import com.restaurant.notification.sse.UserSseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repo;
    private final PreferenceRepository prefRepo;
    private final UserSseService sse;

    public NotificationService(NotificationRepository repo,
                               PreferenceRepository prefRepo,
                               UserSseService sse) {
        this.repo = repo;
        this.prefRepo = prefRepo;
        this.sse = sse;
    }

    public void notifyUser(UUID userId, String message) {

        NotificationPreference pref = prefRepo.findById(userId)
                .orElse(new NotificationPreference());

        Notification n = new Notification(userId, message);
        repo.save(n);

        if (pref.inAppEnabled) {
            sse.send(userId, message);
        }
    }
}
