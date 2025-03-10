package com.example.student_management_backend.dto.reponse;

import java.time.Instant;
import java.time.LocalDateTime;

import com.example.student_management_backend.util.constant.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpdateStudentDTO {
    private Integer id;
    private String fullName;
    private String email;
    private GenderEnum gender;
    private String address;
    private LocalDateTime dob;
    private String phone;
    private String major;
    private Integer year;
    private Instant updatedAt;

}