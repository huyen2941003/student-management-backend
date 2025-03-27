package com.example.student_management_backend.dto.request;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.domain.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String roleName;
    private String fullName;
    private LocalDate dob;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private Integer majorId;
    private Integer departmentId;
    @JsonIgnore
    private transient MultipartFile avatarFile;
    private String avatarPath;
}