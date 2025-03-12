package com.example.student_management_backend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.domain.Student;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.reponse.RegisterResponse;
import com.example.student_management_backend.dto.request.ForgotPasswordRequest;
import com.example.student_management_backend.dto.request.RegisterRequest;
import com.example.student_management_backend.dto.request.ResetPasswordRequest;
import com.example.student_management_backend.repository.RoleRepository;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        // Kiểm tra mã sinh viên đã tồn tại chưa
        if (studentRepository.existsByStudentCode(registerRequest.getStudentCode())) {
            throw new RuntimeException("Mã sinh viên đã tồn tại!");
        }
        Role role = roleRepository.findById(registerRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + registerRequest.getRoleId()));

        // Tạo đối tượng User từ RegisterRequest
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        // Lưu user vào database
        userRepository.save(user);

        // Tạo đối tượng Student từ RegisterRequest
        Student student = new Student();
        student.setUser(user);
        student.setStudentCode(registerRequest.getStudentCode());
        student.setFullName(registerRequest.getFullName());
        student.setDob(registerRequest.getDob());
        student.setGender(registerRequest.getGender());
        student.setMajor(registerRequest.getMajor());
        student.setYear(registerRequest.getYear());
        student.setCreatedAt(Instant.now());
        student.setUpdatedAt(Instant.now());

        // Lưu student vào database
        studentRepository.save(student);

        // Trả về response
        return new RegisterResponse("Đăng ký tài khoản cho sinh viên thành công!", user.getUsername());
    }

}