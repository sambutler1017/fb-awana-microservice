package com.awana.app.subscription.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.awana.app.subscription.client.domain.Notification;

/**
 * Web Notifier Service wraps the common elements of sending web notifications
 * into one call.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Service
public class WebNotifierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebNotifierService.class);

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Send a Web Notification for a given subscription match with the User
     * Notification set.
     * 
     * @param envelope {@link Notification} to be sent.
     */
    public <T extends Notification> void send(T body) {
        LOGGER.info("Sending Web Notification to '{}' with type '{}'", body.getDestination(), body.getBodyType());
        sendNotification(body.getDestination(), body);
    }

    /**
     * Send a Web Notification for a given subscription match with the User
     * Notification set to a single user.
     * 
     * @param envelope    {@link Notification} to be sent.
     * @param sessionUUID The unique session id for the user.
     */
    public <T extends Notification> void send(T body, String sessionUUID) {
        LOGGER.info("Sending Web Notification to '{}' with type '{}'",
                    String.format("%s-%s", body.getDestination(), sessionUUID), body.getBodyType());
        sendNotification(String.format("%s-%s", body.getDestination(), sessionUUID), body);
    }

    /**
     * Helper method for sending a web notification to the desired destination and
     * body.
     * 
     * @param <T>         The generic body type.
     * @param envelope    The body to be sent.
     * @param destination Where the notification should go.
     */
    private <T extends Notification> void sendNotification(String destination, T envelope) {
        template.convertAndSend(destination, envelope);
    }
}
