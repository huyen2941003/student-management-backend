package com.example.student_management_backend.dto.request;

import java.time.LocalDate;

import com.example.student_management_backend.domain.Gender;
import com.example.student_management_backend.util.constant.GenderEnum;

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
    private Integer roleId;
    private String fullName;
    private LocalDate dob;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private Integer majorId;
    private Integer departmentId;
}