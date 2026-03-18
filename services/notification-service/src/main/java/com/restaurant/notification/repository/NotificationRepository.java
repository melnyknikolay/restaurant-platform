
package com.restaurant.notification.repository;

import com.restaurant.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {}
