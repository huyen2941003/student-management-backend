package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.dto.request.GradeRequest;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.dto.response.GradeResponse;
import com.example.student_management_backend.service.GradeService;

import com.example.student_management_backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;
    private final AuthUtil authUtil;
    @SneakyThrows
    @PreAuthorize("")
    @GetMapping("/courseId/{courseId}")
    public List<GradeResponse> getGradesByCourseId(@PathVariable int courseId) {
        Integer studentId = authUtil.loggedInStudentId();
        return gradeService.getGradesByCourseId(courseId, studentId);
    }

    @GetMapping("/userId")
    public List<GradeResponse> getGradesByUserId() throws Exception {
        Integer studentId = authUtil.loggedInStudentId();
        return gradeService.getGradesByUserId(studentId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURE')")
    @GetMapping("/gpa/userId")
    public List<GpaResponse> getGpa() throws Exception {
        Integer studentId = authUtil.loggedInStudentId();
        return gradeService.calculateGpaBySemester(studentId);
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

    @GetMapping("/search")
    public ResponseEntity<Page<Grades>> searchGrades(
            @RequestParam(required = false) Double midtermScore,
            @RequestParam(required = false) Double midtermScoreMin,
            @RequestParam(required = false) Double midtermScoreMax,
            @RequestParam(required = false) Double finalScore,
            @RequestParam(required = false) Double finalScoreMin,
            @RequestParam(required = false) Double finalScoreMax,
            @RequestParam(required = false) Double totalScore,
            @RequestParam(required = false) Double totalScoreMin,
            @RequestParam(required = false) Double totalScoreMax,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) Integer studentId,
            @RequestParam(required = false) Integer courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));

        Page<Grades> grades = gradeService.searchGrades(
                midtermScore, midtermScoreMin, midtermScoreMax,
                finalScore, finalScoreMin, finalScoreMax,
                totalScore, totalScoreMin, totalScoreMax,
                semester, studentId, courseId,
                pageable);

        return ResponseEntity.ok(grades);
    }
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalGPA() throws Exception {
        Integer studentId = authUtil.loggedInStudentId();
        return ResponseEntity.ok(gradeService.getTotalGPA(studentId));
    }

    @GetMapping("/total/by-semester")
    public ResponseEntity<Map<Integer, Double>> getGPABySemester() throws Exception {
        Integer studentId = authUtil.loggedInStudentId();
        return ResponseEntity.ok(gradeService.getGPABySemester(studentId));
    }
}
