package com.example.student_management_backend.dto.reponse.role;

import com.example.student_management_backend.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private long id;
    private String roleName;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.roleName = role.getRoleName();
    }
}