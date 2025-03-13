package com.example.student_management_backend.dto.response.student;

import java.time.LocalDate;
import com.example.student_management_backend.domain.Students;
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
public class StudentResponse {
    private Integer id;
    private String fullName;
    private LocalDate dob;
    private GenderEnum gender;
    private String email;
    private String phone;
    private String address;
    private StatusEnum status;
    private String majorName;
    private String departmentName;
    private String username;
    private String avatar;

    public StudentResponse(Students student) {
        this.id = student.getId();
        this.fullName = student.getFullName();
        this.dob = student.getDob();
        this.gender = student.getGender();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.address = student.getAddress();
        this.status = student.getStatus();
        this.avatar = student.getAvatar();
        this.majorName = student.getMajors() != null ? student.getMajors().getMajorName() : null;
        this.departmentName = student.getDepartments() != null ? student.getDepartments().getDepartmentName() : null;
        this.username = student.getUser() != null ? student.getUser().getUsername() : null;
    }
}