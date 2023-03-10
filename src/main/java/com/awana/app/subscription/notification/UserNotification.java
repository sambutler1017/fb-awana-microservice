package com.awana.app.subscription.notification;

import com.awana.app.subscription.client.domain.Notification;
import com.awana.app.subscription.client.domain.NotificationType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Subscription Notification
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Schema(description = "User Subscription for notifications")
public class UserNotification extends Notification {
    @Schema(description = "The user id of the new user.")
    private int userId;

    @Schema(description = "The full name of the new user.")
    private String name;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public NotificationType getBodyType() {
        return NotificationType.USER;
    }
}