package com.example.student_management_backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.User;

@Service
public interface AccountService {
    public ResponseEntity<?> register(User user);

    public ResponseEntity<?> signIn(User user);
}
