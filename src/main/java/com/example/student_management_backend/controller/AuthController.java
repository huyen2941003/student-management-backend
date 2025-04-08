package com.example.student_management_backend.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.example.student_management_backend.service.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok("Logout thành công");
    }

    @Autowired
    private FileStorageService fileStorageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @Valid @ModelAttribute RegisterRequest registerRequest,
            BindingResult bindingResult,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = fileStorageService.saveAvatarFile(avatarFile);
                registerRequest.setAvatarFile(avatarFile); // Set file
                registerRequest.setAvatarPath(avatarPath); // Set path
            }

            RegisterResponse response = authService.register(registerRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lưu ảnh đại diện");
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

        Students student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với email: " + email));
        User user = student.getUser();

        /*
         * String resetToken = generateResetToken(user);
         * String resetLink = "http://yourdomain.com/reset-password?token=" +
         * resetToken;
         * emailService.sendPasswordResetEmail(student.getEmail(), resetLink);
         */

        String otp = generateOtp();
        user.setResetToken(otp);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(5)); // OTP thường hết hạn sau 5 phút
        userRepository.save(user);
        emailService.sendPasswordResetEmail(student.getEmail(), otp);
        return ResponseEntity.ok("Email đặt lại mật khẩu đã được gửi.");
    }

    private String generateResetToken(User user) {
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        return resetToken;
    }

    private String generateOtp() {
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        return String.valueOf(otpNumber);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String otp, @RequestParam String newPassword) {
        /*
         * User user = userRepository.findByResetToken(token)
         * .orElseThrow(() -> new RuntimeException("Token không hợp lệ"));
         */
        User user = userRepository.findByResetToken(otp)
                .orElseThrow(() -> new RuntimeException("Mã OTP không hợp lệ"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã OTP đã hết hạn");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        authService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}