package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.request.FcmTokenRequest;
import com.example.student_management_backend.repository.UserRepository;
import com.example.student_management_backend.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/user")
public class UserController {

    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update-fcm-token")
    public ResponseEntity<String> updateFcmToken(@RequestBody FcmTokenRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new RuntimeException("User không tồn tại"));
        user.setFcmToken(request.getFcmToken());
        userRepository.save(user);
        return ResponseEntity.ok("FCM Token đã được cập nhật!");
    }

}
