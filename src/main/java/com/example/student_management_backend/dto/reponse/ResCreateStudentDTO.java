package com.example.student_management_backend.dto.reponse;

import java.time.Instant;
import java.time.LocalDateTime;

import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.util.constant.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResCreateStudentDTO {
    private Integer id;
    private String fullName;
    private String email;
    private GenderEnum gender;
    private String address;
    private LocalDateTime dob;
    private String phone;
    private String major;
    private Integer year;
    private Instant createdAt;

    private User role;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUser {
        private long id;
        private String roleName;
    }
}
