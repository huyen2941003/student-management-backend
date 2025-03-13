package com.example.student_management_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.dto.request.StudentRequest;
import com.example.student_management_backend.dto.response.role.RoleResponse;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
        try {
            StudentResponse response = studentService.getStudentById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/students")
    public ResponseEntity<?> getAllStudent() {
        List<StudentResponse> response = studentService.getAllStudent();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/students/{id}")
    public ResponseEntity<StudentResponse> updateStudentForAdmin(
            @PathVariable Integer id,
            @ModelAttribute StudentRequest studentRequest,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {
        StudentResponse updatedStudent = studentService.updateStudentForAdmin(id, studentRequest, avatarFile);
        return ResponseEntity.ok(updatedStudent);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentResponse> updateStudentForStudent(
            @PathVariable Integer id,
            @ModelAttribute StudentRequest studentRequest,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {
        StudentResponse updatedStudent = studentService.updateStudentForStudent(id, studentRequest, avatarFile);
        return ResponseEntity.ok(updatedStudent);
    }
}
