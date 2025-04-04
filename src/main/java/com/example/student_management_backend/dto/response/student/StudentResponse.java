package com.example.student_management_backend.dto.response.student;

import java.time.LocalDate;

import com.example.student_management_backend.domain.Gender;
import com.example.student_management_backend.domain.Status;
import com.example.student_management_backend.domain.Students;

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
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private Status status;
    private String majorName;
    private String departmentName;
    private String username;
    private String avatar;
    private String roleName;

    public StudentResponse(Students student) {
        if (student != null) {
            this.id = student.getId();
            this.fullName = student.getFullName();
            this.dob = student.getDob();
            this.gender = student.getGender();
            this.email = student.getEmail();
            this.phone = student.getPhone();
            this.address = student.getAddress();
            this.status = student.getStatus();
            this.avatar = student.getAvatar();

            if (student.getMajors() != null) {
                this.majorName = student.getMajors().getMajorName();
            }

            if (student.getDepartments() != null) {
                this.departmentName = student.getDepartments().getDepartmentName();
            }

            if (student.getUser() != null) {
                this.username = student.getUser().getUsername();
            }

            // Kiểm tra null an toàn
            if (student.getUser() != null && student.getUser().getRole() != null) {
                this.roleName = student.getUser().getRole().getRole();
            } else {
                this.roleName = null;
            }
        }
    }
}