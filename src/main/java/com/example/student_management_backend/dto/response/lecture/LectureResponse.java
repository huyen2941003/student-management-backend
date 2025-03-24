package com.example.student_management_backend.dto.response.lecture;

import java.time.LocalDate;

import com.example.student_management_backend.domain.Gender;
import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.domain.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureResponse {
    private Integer id;
    private String fullName;
    private LocalDate dob;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private Status status;
    private String departmentName;
    private String username;
    private String avatar;

    public LectureResponse(Lectures lectures) {
        if (lectures != null) {
            this.id = lectures.getId();
            this.fullName = lectures.getFullName();
            this.dob = lectures.getDob();
            this.gender = lectures.getGender();
            this.email = lectures.getEmail();
            this.phone = lectures.getPhone();
            this.address = lectures.getAddress();
            this.status = lectures.getStatus();
            this.avatar = lectures.getAvatar();

            if (lectures.getDepartments() != null) {
                this.departmentName = lectures.getDepartments().getDepartmentName();
            }

            if (lectures.getUser() != null) {
                this.username = lectures.getUser().getUsername();
            }
        }
    }
}