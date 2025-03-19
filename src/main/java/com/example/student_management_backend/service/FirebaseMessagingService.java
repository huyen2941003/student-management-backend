package com.example.student_management_backend.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {
    public String sendNotification(String token, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token) // Token của thiết bị nhận thông báo
                .setNotification(notification)
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }
}
