package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.student_management_backend.dto.StudentDTO;
import com.example.student_management_backend.dto.reponse.student.StudentResponse;
import com.example.student_management_backend.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Lấy thông tin sinh viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
        try {
            StudentResponse response = studentService.getStudentById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Cập nhật thông tin sinh viên
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
        try {
            StudentResponse response = studentService.updateStudent(id, studentDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}