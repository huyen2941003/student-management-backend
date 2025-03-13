package com.example.student_management_backend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.example.student_management_backend.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.dto.request.RegisterRequest;
import com.example.student_management_backend.dto.response.RegisterResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.repository.MajorsRepository;
import com.example.student_management_backend.repository.RoleRepository;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;
import com.example.student_management_backend.util.constant.StatusEnum;

import lombok.RequiredArgsConstructor;

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
                // Kiểm tra nếu username được truyền vào và roleId = 1
                if (registerRequest.getUsername() != null && registerRequest.getRoleId() == 1) {
                        // Chỉ đăng ký User
                        return registerUserOnly(registerRequest);
                } else {
                        // Nếu không có username, tạo username ngẫu nhiên và đăng ký cả User và Student
                        return registerUserAndStudent(registerRequest);
                }
        }

        private RegisterResponse registerUserOnly(RegisterRequest registerRequest) {
                // Kiểm tra username đã tồn tại chưa
                if (userRepository.existsByUsername(registerRequest.getUsername())) {
                        throw new RuntimeException("Username đã tồn tại!");
                }

                // Lấy role từ database
                Role role = roleRepository.findById(registerRequest.getRoleId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Role not found with id: " + registerRequest.getRoleId()));

                // Tạo và lưu User
                User user = new User();
                user.setUsername(registerRequest.getUsername());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setRole(role);

                User savedUser = userRepository.save(user);

                return new RegisterResponse("Đăng ký tài khoản thành công!", savedUser.getUsername());
        }

        private RegisterResponse registerUserAndStudent(RegisterRequest registerRequest) {
                // Tạo username ngẫu nhiên
                LocalDateTime createdAt = LocalDateTime.now();
                String username = generateStudentId(createdAt);

                // Kiểm tra username đã tồn tại chưa
                if (userRepository.existsByUsername(username)) {
                        throw new RuntimeException("Username đã tồn tại!");
                }

                // Lấy role từ database
                Role role = roleRepository.findById(registerRequest.getRoleId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Role not found with id: " + registerRequest.getRoleId()));

                // Tạo và lưu User
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setRole(role);

                User savedUser = userRepository.save(user);

                // Lấy major và department từ database
                Majors major = majorsRepository.findById(registerRequest.getMajorId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Major not found with id: " + registerRequest.getMajorId()));

                Departments department = departmentsRepository.findById(registerRequest.getDepartmentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Department not found with id: " + registerRequest.getDepartmentId()));

                // Tạo và lưu Student
                Students student = Students.builder()
                                .fullName(registerRequest.getFullName())
                                .dob(registerRequest.getDob())
                                .gender(registerRequest.getGender())
                                .email(registerRequest.getEmail())
                                .phone(registerRequest.getPhone())
                                .address(registerRequest.getAddress())
                                .status(Status.Active)
                                .user(savedUser)
                                .majors(major)
                                .departments(department)
                                .build();

                studentRepository.save(student);

                return new RegisterResponse("Đăng ký tài khoản và sinh viên thành công!", savedUser.getUsername());
        }

        private String generateStudentId(LocalDateTime createdAt) {
                String year = createdAt.format(DateTimeFormatter.ofPattern("yyyy"));
                int randomDigits = 100000 + random.nextInt(900000);

                return year + randomDigits;
        }

}