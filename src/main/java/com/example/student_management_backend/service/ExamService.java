package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.repository.ExamsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService implements IExamService {
    private final ExamsRepository examsRepository;

    public ExamService(ExamsRepository examsRepository) {
        this.examsRepository = examsRepository;
    }

    @Override
    public List<ExamsScheduleResponse> getExams(int userId) {

        List<ExamsScheduleResponse> usExamsScheduleResponses = examsRepository.getExams(userId);
        return usExamsScheduleResponses;
    }

    @Override
    public Exams findExamById(int id) {
        return examsRepository.findById(id).get();
    }
}
