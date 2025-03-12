package com.example.student_management_backend.dto.request;

import java.time.LocalDateTime;

import com.example.student_management_backend.util.constant.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;

    private String studentCode;
    private String fullName;
    private LocalDateTime dob;
    private GenderEnum gender;
    private String major;
    private Integer year;

    private Long roleId;

}