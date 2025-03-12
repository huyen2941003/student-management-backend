package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.request.FcmTokenRequest;
import com.example.student_management_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    @PostMapping("/update-fcm-token")
    public ResponseEntity<String> updateFcmToken(@RequestBody FcmTokenRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new RuntimeException("User không tồn tại")
        );
        user.setFcmToken(request.getFcmToken());
        userRepository.save(user);
        return ResponseEntity.ok("FCM Token đã được cập nhật!");
    }
}
