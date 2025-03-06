package com.example.student_management_backend.service.impl;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Notify;
import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.repository.RoleRepository;
import com.example.student_management_backend.repository.UserRepository;
import com.example.student_management_backend.service.AccountService;

@AllArgsConstructor
@Service
public class AccountServceImpl implements AccountService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> register(User user) {
        if (this.userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new Notify("Username này đã tồn tại"));
        }
        if (user.getPassword() == null) {
            System.out.println("Mật khẩu không thể là null");
            return ResponseEntity.badRequest().body(new Notify("Mật khẩu không thể là null"));
        }
        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        List<Role> defaultRoles = roleRepository.findByRoleName("ROLE_USER");
        user.setRoleList(defaultRoles);
        return ResponseEntity.ok("Đăng ký thành công");
    }

    @Override
    public ResponseEntity<?> signIn(User user) {
        User existsUser = this.userRepository.findByUsername(user.getUsername());
        if (existsUser == null || !passwordEncoder.matches(user.getPassword(), existsUser.getPassword())) {
            return ResponseEntity.badRequest().body(new Notify("Tài khoản hoặc mật khẩu sai!"));
        }
        return ResponseEntity.ok("Đăng nhập thành công!");
    }
}
