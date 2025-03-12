package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @GetMapping("/{userId}")
    public List<Courses> getCourseByUserId(@PathVariable int userId)
    {
        return courseService.getCourseByUserId(userId);
    }
}
