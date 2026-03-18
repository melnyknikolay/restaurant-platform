
package com.restaurant.notification.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class NotificationPreference {

    @Id
    private UUID userId;

    public boolean emailEnabled = true;
    public boolean inAppEnabled = true;
}
