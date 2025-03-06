package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody User user) {
        ResponseEntity<?> responseEntity = this.accountService.register(user);
        return responseEntity;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> loginUser(@Validated @RequestBody User user) {
        ResponseEntity<?> response = this.accountService.signIn(user);
        return response;
    }
}
