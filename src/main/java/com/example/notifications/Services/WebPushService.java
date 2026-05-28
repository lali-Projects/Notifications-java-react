package com.example.notifications.Services;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebPushService {

//    private final String PUBLIC_KEY = "YOUR_PUBLIC_KEY";
//    private final String PRIVATE_KEY = "YOUR_PRIVATE_KEY";

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    public void sendPush(com.example.notifications.Entities.Notification notificationEntity) throws Exception {
        PushService pushService = new PushService(publicKey, privateKey, "mailto:admin@example.com");

        // חילוץ פרטי המשתמש מההתראה
        var user = notificationEntity.getUser();

        Subscription sub = new Subscription(
                user.getPushEndpoint(),
                new Subscription.Keys(user.getPushP256dh(), user.getPushAuth())
        );

        // יצירת תוכן ההודעה
        String message = notificationEntity.isTake() ?
                "הגיע הזמן ליטול: " + notificationEntity.getMedication().getName() :
                "תזכורת כללית: " + notificationEntity.getMedication().getName();

        String payload = String.format("{\"title\": \"תזכורת תרופה\", \"body\": \"%s\", \"id\": %d}",
                message, notificationEntity.getId());

        Notification n = new Notification(sub, payload);
        pushService.send(n);
    }
}