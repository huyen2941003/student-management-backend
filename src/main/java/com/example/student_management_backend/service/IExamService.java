package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;

import java.util.List;

public interface IExamService {
    List<ExamsScheduleResponse> getExams(int userId);
    Exams findExamById(int id);

    List<Exams> getAllExam();

    void deleteExamById(int id);

    Exams createExam(ExamRequest request);
}
