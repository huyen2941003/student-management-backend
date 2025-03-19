package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.dto.request.DepartmentsRequest;
import com.example.student_management_backend.dto.response.department.DepartmentsResponse;
import com.example.student_management_backend.service.DepartmentsService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
public class DepartmentController {

    @Autowired
    private DepartmentsService departmentsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DepartmentsResponse> createDepartment(@RequestBody DepartmentsRequest request) {
        DepartmentsResponse response = departmentsService.createDepartment(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<DepartmentsResponse>> getAllDepartments() {
        List<DepartmentsResponse> responses = departmentsService.getAllDepartments();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsResponse> getDepartmentById(@PathVariable Integer id) {
        DepartmentsResponse response = departmentsService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentsResponse> updateDepartment(@PathVariable Integer id,
            @RequestBody DepartmentsRequest request) {
        DepartmentsResponse response = departmentsService.updateDepartment(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        departmentsService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    // Nhập tên khoa hoặc mô tả để tìm kiếm khoa
    @GetMapping("/search")
    public ResponseEntity<List<Departments>> searchDepartments(
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String description) {
        List<Departments> departments = departmentsService.searchDepartments(departmentName, description);
        return ResponseEntity.ok(departments);
    }

}