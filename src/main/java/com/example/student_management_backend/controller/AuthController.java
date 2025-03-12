package com.example.student_management_backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.request.RegisterRequest;
import com.example.student_management_backend.dto.response.JwtAuthenticationResponse;
import com.example.student_management_backend.dto.response.RegisterResponse;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;
import com.example.student_management_backend.security.CustomUserDetails;
import com.example.student_management_backend.security.JwtTokenProvider;
import com.example.student_management_backend.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import com.example.student_management_backend.dto.request.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok("Logout thành công");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            RegisterResponse response = authService.register(registerRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private com.example.student_management_backend.service.email.EmailService emailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        // Tìm student bằng email
        Students student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với email: " + email));

        // Lấy user từ student
        User user = student.getUser();

        // Tạo liên kết đặt lại mật khẩu (ví dụ: sử dụng token)
        String resetToken = generateResetToken(user);
        String resetLink = "http://yourwebsite.com/reset-password?token=" + resetToken;

        // Gửi email chứa liên kết đặt lại mật khẩu
        emailService.sendPasswordResetEmail(student.getEmail(), resetLink);

        return ResponseEntity.ok("Email đặt lại mật khẩu đã được gửi.");
    }

    private String generateResetToken(User user) {
        // Logic để tạo token đặt lại mật khẩu
        // Ví dụ: sử dụng UUID hoặc JWT
        return UUID.randomUUID().toString();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        // Tìm user bằng token
        User user = userRepository.findByFcmToken(token)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ"));

        // Cập nhật mật khẩu mới
        user.setPassword(newPassword);
        user.setFcmToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công.");
    }
}
