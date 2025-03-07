package com.example.student_management_backend.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRegisterRequest {
    private String username;
    private String password;

    private String fullName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phone;
    private String major;
    private Integer year;
}
