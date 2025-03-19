package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.service.IExamService;
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

    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public ResponseEntity<Page<Exams>> searchExams(
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

        // Tạo đối tượng Pageable để phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));

        // Gọi service để thực hiện tìm kiếm
        Page<Exams> exams = examService.searchExams(
                examDate, examDateFrom, examDateTo,
                startTime, endTime,
                room, courseName, classId,
                pageable);

        return ResponseEntity.ok(exams);
    }
}
