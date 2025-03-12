package com.example.student_management_backend.dto.reponse.student;

import java.time.LocalDateTime;

import com.example.student_management_backend.domain.Student;
import com.example.student_management_backend.util.constant.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Integer id;
    private String studentCode;
    private String email;
    private String fullName;
    private LocalDateTime dob;
    private GenderEnum gender;
    private String phone;
    private String address;
    private String major;
    private Integer year;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.studentCode = student.getStudentCode();
        this.email = student.getEmail();
        this.fullName = student.getFullName();
        this.dob = student.getDob();
        this.gender = student.getGender();
        this.phone = student.getPhone();
        this.address = student.getAddress();
        this.major = student.getMajor();
        this.year = student.getYear();
    }
}