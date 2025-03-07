package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
    private final IExamService examService;
    @GetMapping("/{userId}")
    public List<ExamsScheduleResponse> getExam(@PathVariable int userId)
    {
        //lay userId tu token
        return examService.getExams(userId);
    }
//    @GetMapping("/{id}")
//    public Exams getExams(@PathVariable int id)
//    {
//        return examService.findExamById(id);
//    }
}
