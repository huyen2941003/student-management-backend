package com.example.student_management_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.reponse.ResCreateStudentDTO;
import com.example.student_management_backend.service.StudentService;
import com.example.student_management_backend.util.annotation.ApiMessage;
import com.example.student_management_backend.util.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

}