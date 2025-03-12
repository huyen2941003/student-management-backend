package com.example.student_management_backend.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.dto.RoleDTO;
import com.example.student_management_backend.dto.response.role.RoleResponse;
import com.example.student_management_backend.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Tạo mới role
    public RoleResponse createRole(RoleDTO roleDTO) {
        if (roleRepository.existsByRole(roleDTO.getRoleName())) {
            throw new RuntimeException("Role name đã tồn tại!");
        }
        Role role = new Role();
        role.setRole(roleDTO.getRoleName());
        role.setCreatedAt(Instant.now());
        role.setUpdatedAt(Instant.now());

        role = roleRepository.save(role);
        return new RoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleResponse::new)
                .collect(Collectors.toList());
    }

    public RoleResponse getRoleById(long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có id: " + id));
        return new RoleResponse(role);
    }

    public RoleResponse updateRole(long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có id: " + id));

        role.setRole(roleDTO.getRoleName());
        role.setUpdatedAt(Instant.now());

        role = roleRepository.save(role);
        return new RoleResponse(role);
    }

    public void deleteRole(long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có id: " + id));

        if (!role.getUsers().isEmpty()) {
            throw new RuntimeException("Không thể xóa role này vì đang kết nối với user");
        }

        roleRepository.delete(role);
    }
}