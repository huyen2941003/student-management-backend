package com.example.student_management_backend.dto.reponse;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateUserDTO {
    private Integer id;
    private String username;
    private Instant createdAt;

    private RoleUser role;

    // Lớp con RoleUser
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUser {
        private long id;
        private String roleName;
    }
}