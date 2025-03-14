package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.dto.request.GradeRequest;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.dto.response.GradeResponse;
import com.example.student_management_backend.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PreAuthorize("")
    @GetMapping("/courseId/{courseId}")
    public List<GradeResponse> getGradesByCourseId(@PathVariable int courseId) {
        int userId = 1;
        return gradeService.getGradesByCourseId(courseId, userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @GetMapping("/{userId}")
    public List<GradeResponse> getGradesByUserId(@PathVariable int userId) {
        return gradeService.getGradesByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @GetMapping("/gpa/{userId}")
    public List<GpaResponse> getGpa(@PathVariable int userId) {
        return gradeService.calculateGpaBySemester(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @PostMapping("")
    public ResponseEntity<Grades> createGrade(@RequestBody GradeRequest request) throws Exception {
        Grades grade = gradeService.createGrade(request);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @GetMapping("")
    public ResponseEntity<List<Grades>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @DeleteMapping
    public ResponseEntity<Object> deleteGrade(@PathVariable int id) throws Exception {
        gradeService.deleteGradeById(id);
        return ResponseEntity.ok().body("Grade deleted successfully");

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @PutMapping
    public ResponseEntity<Grades> updateGrade(@RequestBody GradeRequest request) throws Exception {
        Grades grade = gradeService.updateGradeById(request);
        return new ResponseEntity<>(grade, HttpStatus.OK);

    }
}
