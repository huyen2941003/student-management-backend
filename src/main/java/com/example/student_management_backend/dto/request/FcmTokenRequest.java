package com.example.student_management_backend.dto.request;

public class FcmTokenRequest {
    private Integer userId;
    private String fcmToken;

    // Constructors
    public FcmTokenRequest() {}

    public FcmTokenRequest(Integer userId, String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
    }

    // Getters và Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}

