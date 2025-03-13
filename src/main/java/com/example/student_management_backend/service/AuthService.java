package com.example.student_management_backend.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.request.ForgotPasswordRequest;
import com.example.student_management_backend.dto.request.RegisterRequest;
import com.example.student_management_backend.dto.request.ResetPasswordRequest;
import com.example.student_management_backend.dto.response.RegisterResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.repository.MajorsRepository;
import com.example.student_management_backend.repository.RoleRepository;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;
import com.example.student_management_backend.util.constant.StatusEnum;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MajorsRepository majorsRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    private final Random random = new Random();
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        // Tìm role từ roleId
        Role role = roleRepository.findById(registerRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + registerRequest.getRoleId()));

        // Tạo đối tượng User từ RegisterRequest
        User user = new User();
        LocalDateTime createdAt = LocalDateTime.now();
        String username = generateStudentId(createdAt);
        user.setUsername(username);

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);

        // Lưu user vào database
        User savedUser = userRepository.save(user); // Lưu User trước
        System.out.println("User ID after save: " + savedUser.getId()); // Debug

        System.out.println("User ID after save: " + user.getId());
        // Tìm majors và departments từ request
        Majors major = majorsRepository.findById(registerRequest.getMajorId())
                .orElseThrow(() -> new RuntimeException("Major not found with id: " + registerRequest.getMajorId()));

        Departments department = departmentsRepository.findById(registerRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException(
                        "Department not found with id: " + registerRequest.getDepartmentId()));

        // Tạo đối tượng Student từ RegisterRequest
        Students student = Students.builder()
                .fullName(registerRequest.getFullName())
                .dob(registerRequest.getDob())
                .gender(registerRequest.getGender())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .status(StatusEnum.ACTIVE)
                .user(savedUser)
                .majors(major)
                .departments(department)
                .build();

        // Lưu student vào database
        studentRepository.save(student);

        // Trả về response
        return new RegisterResponse("Đăng ký tài khoản cho sinh viên thành công!", user.getUsername());
    }
    private String generateStudentId(LocalDateTime createdAt) {
        String year = createdAt.format(DateTimeFormatter.ofPattern("yyyy"));
        int randomDigits = 100000 + random.nextInt(900000); // Đảm bảo có đủ 6 chữ số

        return year + randomDigits;
    }

}