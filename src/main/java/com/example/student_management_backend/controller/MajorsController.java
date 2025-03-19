package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.dto.request.MajorsRequest;
import com.example.student_management_backend.dto.response.major.MajorsResponse;
import com.example.student_management_backend.service.MajorsService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/majors")
public class MajorsController {

    @Autowired
    private MajorsService majorsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MajorsResponse> createMajor(@RequestBody MajorsRequest request) {
        MajorsResponse response = majorsService.createMajor(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<MajorsResponse>> getAllMajors() {
        List<MajorsResponse> responses = majorsService.getAllMajors();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MajorsResponse> getMajorById(@PathVariable Integer id) {
        MajorsResponse response = majorsService.getMajorById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MajorsResponse> updateMajor(@PathVariable Integer id, @RequestBody MajorsRequest request) {
        MajorsResponse response = majorsService.updateMajor(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable Integer id) {
        majorsService.deleteMajor(id);
        return ResponseEntity.noContent().build();
    }

    // Nhập tên chuyên ngành hoặc tên khoa để tìm kiếm chuyên ngành
    @GetMapping("/search")
    public ResponseEntity<List<Majors>> searchMajors(
            @RequestParam(required = false) String majorName,
            @RequestParam(required = false) String departmentName) {
        List<Majors> majors = majorsService.searchMajors(majorName, departmentName);
        return ResponseEntity.ok(majors);
    }
}