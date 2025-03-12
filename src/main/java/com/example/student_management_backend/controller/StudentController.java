package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService studentService;
    @GetMapping("/{id}")
    public Students getStudentById(@PathVariable  int id) throws Exception {
        return studentService.getStudentById(id);
    }
}
