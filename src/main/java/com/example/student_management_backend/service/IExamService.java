package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IExamService {
    List<ExamsScheduleResponse> getExams(int userId);

    Exams findExamById(int id);

    List<ExamsScheduleResponse> getAllExam();

    void deleteExamById(int id);

    Exams createExam(ExamRequest request);

    Page<Exams> searchExams(
            LocalDate examDate,
            LocalDate examDateFrom,
            LocalDate examDateTo,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String room,
            String courseName,
            Integer classId,
            Pageable pageable);

    Exams updateExam(ExamRequest request, int id) throws Exception;
}
