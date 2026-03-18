
package com.restaurant.notification.repository;

import com.restaurant.notification.domain.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<NotificationPreference, UUID> {}
