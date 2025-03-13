package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.dto.request.GradeRequest;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.dto.response.GradeResponse;
import com.example.student_management_backend.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;
    @GetMapping("/courseId/{courseId}")
    public List<GradeResponse> getGradesByCourseId(@PathVariable int courseId)
    {
        int userId=1;
        return gradeService.getGradesByCourseId(courseId,userId);
    }
    @GetMapping("/{userId}")
    public List<GradeResponse> getGradesByUserId(@PathVariable int userId)
    {
        return gradeService.getGradesByUserId(userId);
    }
    @GetMapping("/gpa/{userId}")
    public List<GpaResponse> getGpa(@PathVariable int userId) {
        return gradeService.calculateGpaBySemester(userId);
    }
    @PostMapping("")
    public ResponseEntity<Grades> createGrade
            (@RequestBody GradeRequest request) throws Exception {
        Grades grade = gradeService.createGrade(request);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<Grades>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }
    @DeleteMapping
    public ResponseEntity<Object> deleteGrade(@PathVariable int id) throws Exception {
        gradeService.deleteGradeById(id);
        return ResponseEntity.ok().body("Grade deleted successfully");

    }
    @PutMapping
    public ResponseEntity<Grades> updateGrade(@RequestBody GradeRequest request) throws Exception {
        Grades grade = gradeService.updateGradeById(request);
        return new ResponseEntity<>(grade, HttpStatus.OK);

    }
}
