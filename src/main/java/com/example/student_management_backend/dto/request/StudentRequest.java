package com.example.student_management_backend.dto.request;

import java.time.LocalDate;
import com.example.student_management_backend.util.constant.GenderEnum;
import com.example.student_management_backend.util.constant.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String fullName;
    private LocalDate dob;
    private GenderEnum gender;
    private String email;
    private String phone;
    private String address;
    private StatusEnum status;
    private Integer majorId;
    private Integer departmentId;
    private String avatar;

}