package com.example.student_management_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.service.StudentService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
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

    @PutMapping("/students/{id}")
    public ResponseEntity<Students> updateStudent(@PathVariable Integer id, @RequestBody Students updatedStudent) {
        Students student = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/admin/students/{id}")
    public ResponseEntity<Students> updateStudentByAdmin(@PathVariable Integer id,
            @RequestBody Students updateStudentByAdmin) {
        Students student = studentService.updateStudentByAdmin(id, updateStudentByAdmin);
        return ResponseEntity.ok(student);
    }

}
