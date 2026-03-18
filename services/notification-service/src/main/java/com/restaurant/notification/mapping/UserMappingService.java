
package com.restaurant.notification.mapping;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserMappingService {

    public UUID resolveUserId(String orderId) {
        return UUID.nameUUIDFromBytes(orderId.getBytes());
    }
}
