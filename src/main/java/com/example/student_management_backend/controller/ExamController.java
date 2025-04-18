package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.service.IExamService;
import com.example.student_management_backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
    private final IExamService examService;
    private final AuthUtil authUtil;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/userId")
    public List<ExamsScheduleResponse> getExam() throws Exception {
        Integer studentId = authUtil.loggedInStudentId();
        return examService.getExams(studentId);
    }

    // @GetMapping("/{id}")
    // public Exams getExams(@PathVariable int id)
    // {
    // return examService.findExamById(id);
    // }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ExamsScheduleResponse>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExam());
    }
    @PreAuthorize("hasAnyRole('ADMIN','LECTURE')")
    @GetMapping("/{id}")
    public ResponseEntity<ExamsScheduleResponse> getExams(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(examService.getExamById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Exams> createExam(@RequestBody ExamRequest request) throws Exception {
        Exams exams = examService.createExam(request);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','LECTURE')")
    @PutMapping("{id}")
    public ResponseEntity<Exams> updateExam(@RequestBody ExamRequest request,@PathVariable int id) throws Exception {
        Exams exams = examService.updateExam(request,id);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExam(@PathVariable int id) {

        examService.deleteExamById(id);
        return ResponseEntity.ok().body("Schedule deleted successfully");

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<Page<ExamsScheduleResponse>> searchExams(
            @RequestParam(required = false) LocalDate examDate,
            @RequestParam(required = false) LocalDate examDateFrom,
            @RequestParam(required = false) LocalDate examDateTo,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) String room,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) Integer classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "examDate,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));

        Page<Exams> exams = examService.searchExams(
                examDate, examDateFrom, examDateTo,
                startTime, endTime,
                room, courseName, classId,
                pageable);

        Page<ExamsScheduleResponse> response = exams.map(ExamsScheduleResponse::new);

        return ResponseEntity.ok(response);
    }
}
