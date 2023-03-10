package com.awana.app.subscription.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.awana.app.subscription.client.domain.NotificationSocket;
import com.awana.app.subscription.client.domain.UserPrincipal;
import com.awana.app.subscription.notification.UserNotification;
import com.awana.app.subscription.openapi.TagSubscription;
import com.awana.app.subscription.service.SubscriptionNotifierService;
import com.awana.app.user.client.domain.WebRole;
import com.awana.common.annotations.interfaces.HasAccess;
import com.awana.common.annotations.interfaces.RestApiController;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/subscription-app")
@RestApiController
@TagSubscription
public class SubscriptionTestController {

    @Autowired
    private SubscriptionNotifierService service;

    /**
     * Will get the active users connected to the websocket session.
     * 
     * @return List of SimpUser connections.
     */
    @Operation(summary = "Get's a list of active user sessions", description = "Will return a list of SimpUser objects of connected sessions.")
    @GetMapping(path = "/users")
    @HasAccess(WebRole.DEVELOPER)
    public List<UserPrincipal> getActiveSessionUsers() {
        return service.getActiveUserSessions();
    }

    /**
     * Test endpoint for sending a notification body to everyone.
     */
    @PostMapping(path = "/notification")
    @HasAccess(WebRole.DEVELOPER)
    public void pushGeneralNotification() {
        UserNotification user = new UserNotification();
        user.setName("TEST USER");
        user.setUserId(15);
        service.send(user);
    }

    /**
     * Test endpoint for sending a notification body to the given user.
     */
    @PostMapping(path = "/user/{userId}/notification")
    @HasAccess(WebRole.DEVELOPER)
    public void pushUserNotification(@PathVariable int userId) {
        UserNotification user = new UserNotification();
        user.setName("TEST USER");
        user.setUserId(15);
        service.sendToUser(user, userId);
    }

    /**
     * Test endpoint for sending a notification body to the given system.
     */
    @PostMapping(path = "/system/{uuid}/notification")
    @HasAccess(WebRole.DEVELOPER)
    public void pushSystemNotification(@PathVariable String uuid) {
        UserNotification user = new UserNotification();
        user.setName("TEST USER");
        user.setUserId(15);
        service.send(user, NotificationSocket.QUEUE_SYSTEM_NOTIFICATION, uuid);
    }
}
