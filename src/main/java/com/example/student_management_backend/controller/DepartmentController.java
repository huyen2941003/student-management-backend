package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.student_management_backend.dto.request.DepartmentsRequest;
import com.example.student_management_backend.dto.response.department.DepartmentsResponse;
import com.example.student_management_backend.service.DepartmentsService;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentsService departmentsService;

    @PostMapping
    public ResponseEntity<DepartmentsResponse> createDepartment(@RequestBody DepartmentsRequest request) {
        DepartmentsResponse response = departmentsService.createDepartment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentsResponse>> getAllDepartments() {
        List<DepartmentsResponse> responses = departmentsService.getAllDepartments();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsResponse> getDepartmentById(@PathVariable Integer id) {
        DepartmentsResponse response = departmentsService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentsResponse> updateDepartment(@PathVariable Integer id,
            @RequestBody DepartmentsRequest request) {
        DepartmentsResponse response = departmentsService.updateDepartment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        departmentsService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}