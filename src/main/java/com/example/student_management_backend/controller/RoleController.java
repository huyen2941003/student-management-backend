package com.example.student_management_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_backend.dto.request.RoleRequest;
import com.example.student_management_backend.dto.response.role.RoleResponse;
import com.example.student_management_backend.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Tạo mới role
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse response = roleService.createRole(roleRequest);
        return ResponseEntity.ok(response);
    }

    // Lấy danh sách tất cả roles
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<RoleResponse> response = roleService.getAllRoles();
        return ResponseEntity.ok(response);
    }

    // Lấy role theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable long id) {
        RoleResponse response = roleService.getRoleById(id);
        return ResponseEntity.ok(response);
    }

    // Cập nhật role
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable long id, @RequestBody RoleRequest roleRequest) {
        RoleResponse response = roleService.updateRole(id, roleRequest);
        return ResponseEntity.ok(response);
    }

    // Xóa role
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}