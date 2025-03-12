package com.example.student_management_backend.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.util.constant.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String studentCode;
    private String email;
    private String fullName;
    private LocalDateTime dob;
    private GenderEnum gender;
    private String phone;
    private String address;
    private String major;
    private Integer year;

}