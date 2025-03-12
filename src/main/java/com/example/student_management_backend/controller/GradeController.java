package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/courseId/{courseId}")
    public List<Grades> getGradesByCourseId(@PathVariable int courseId) {
        int userId = 1;
        return gradeService.getGradesByCourseId(courseId, userId);
    }

    @GetMapping("/{userId}")
    public List<Grades> getGradesByUserId(@PathVariable int userId) {
        return gradeService.getGradesByUserId(userId);
    }

    @GetMapping("/gpa/{userId}")
    public List<GpaResponse> getGpa(@PathVariable int userId) {
        return gradeService.calculateGpaBySemester(userId);
    }
}
