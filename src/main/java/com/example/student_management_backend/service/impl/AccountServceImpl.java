package com.example.student_management_backend.service.impl;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.student_management_backend.domain.Notify;
import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.domain.Student;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.request.UserRegisterRequest;
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
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new Notify("Username này đã tồn tại"));
        }
        if (request.getPassword() == null) {
            return ResponseEntity.badRequest().body(new Notify("Mật khẩu không thể là null"));
        }

        // Tạo User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        List<Role> defaultRoles = roleRepository.findByRoleName("ROLE_USER");
        user.setRoleList(defaultRoles);

        // Tạo Student
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setDob(request.getDob());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setPhone(request.getPhone());
        student.setMajor(request.getMajor());
        student.setYear(request.getYear());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setUser(user);

        // Liên kết User và Student
        user.setStudent(student);

        // Lưu vào database
        userRepository.save(user);

        return ResponseEntity.ok("Đăng ký thành công");
    }

    @Override
    public ResponseEntity<?> login(User user) {
        User existsUser = this.userRepository.findByUsername(user.getUsername());
        if (existsUser == null || !passwordEncoder.matches(user.getPassword(), existsUser.getPassword())) {
            return ResponseEntity.badRequest().body(new Notify("Tài khoản hoặc mật khẩu sai!"));
        }
        return ResponseEntity.ok("Đăng nhập thành công!");
    }
}
