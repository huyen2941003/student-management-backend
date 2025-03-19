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
                if (registerRequest.getUsername() != null && "ROLE_ADMIN".equals(registerRequest.getRoleName())) {
                        return registerUserOnly(registerRequest);
                } else {
                        return registerUserAndStudent(registerRequest);
                }
        }

        private RegisterResponse registerUserOnly(RegisterRequest registerRequest) {
                if (userRepository.existsByUsername(registerRequest.getUsername())) {
                        throw new RuntimeException("Username đã tồn tại!");
                }

                Role role = roleRepository.findByRole(registerRequest.getRoleName())
                                .orElseThrow(() -> new RuntimeException(
                                                "Role not found with name: " + registerRequest.getRoleName()));

                User user = new User();
                user.setUsername(registerRequest.getUsername());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setRole(role);

                User savedUser = userRepository.save(user);

                return new RegisterResponse("Đăng ký tài khoản thành công!", savedUser.getUsername());
        }

        private RegisterResponse registerUserAndStudent(RegisterRequest registerRequest) {
                LocalDateTime createdAt = LocalDateTime.now();
                String username = generateStudentId(createdAt);

                if (userRepository.existsByUsername(username)) {
                        throw new RuntimeException("Username đã tồn tại!");
                }

                Role role = roleRepository.findByRole(registerRequest.getRoleName())
                                .orElseThrow(() -> new RuntimeException(
                                                "Role not found with name: " + registerRequest.getRoleName()));

                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setRole(role);

                User savedUser = userRepository.save(user);

                Majors major = majorsRepository.findById(registerRequest.getMajorId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Major not found with id: " + registerRequest.getMajorId()));

                Departments department = departmentsRepository.findById(registerRequest.getDepartmentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Department not found with id: " + registerRequest.getDepartmentId()));

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