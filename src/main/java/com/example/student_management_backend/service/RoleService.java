package com.example.student_management_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.dto.request.RoleRequest;
import com.example.student_management_backend.dto.response.role.RoleResponse;
import com.example.student_management_backend.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest roleRequest) {
        if (roleRepository.existsByRole(roleRequest.getRoleName())) {
            throw new RuntimeException("Role name đã tồn tại!");
        }
        Role role = new Role();
        role.setRole(roleRequest.getRoleName());
        role = roleRepository.save(role);
        return new RoleResponse(role);
    }

    @PreAuthorize("isAuthenticated()")
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("isAuthenticated()")
    public RoleResponse getRoleById(long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có id: " + id));
        return new RoleResponse(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse updateRole(long id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có id: " + id));

        role.setRole(roleRequest.getRoleName());

        role = roleRepository.save(role);
        return new RoleResponse(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có id: " + id));

        roleRepository.delete(role);
    }
}