package com.example.student_management_backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.User;

@Service
public interface UserService extends UserDetailsService {
    public User findUserByUsername(String username);
}
