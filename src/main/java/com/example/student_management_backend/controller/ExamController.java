package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
    private final IExamService examService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public List<ExamsScheduleResponse> getExam(@PathVariable int userId) {
        // Long userID = authUtil.loggedInUserId();
        return examService.getExams(userId);
    }

    // @GetMapping("/{id}")
    // public Exams getExams(@PathVariable int id)
    // {
    // return examService.findExamById(id);
    // }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Exams>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExam());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Exams> createExam(@RequestBody ExamRequest request) throws Exception {
        Exams exams = examService.createExam(request);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExam(@PathVariable int id) {

        examService.deleteExamById(id);
        return ResponseEntity.ok().body("Schedule deleted successfully");

    }
}
