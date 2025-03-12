package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.ExamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService implements IExamService {

    private final ExamsRepository examsRepository;
    private final CourseClassRepository courseClassRepository;


    @Override
    public List<ExamsScheduleResponse> getExams(int userId) {
        return examsRepository.getExams(userId);
    }

    @Override
    public Exams findExamById(int id) {
        return examsRepository.findById(id).get();
    }

    @Override
    public List<Exams> getAllExam() {
        return examsRepository.findAll();
    }

    @Override
    public void deleteExamById(int id) {
        try {
            Exams exams = examsRepository.findById(id).orElseThrow(
                    ()-> new Exception("Exam dont exist")
            );
            examsRepository.delete(exams);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Exams createExam(ExamRequest request) throws Exception {
        CourseClass classes =courseClassRepository.findById(request.getClassesId()).orElseThrow(
                () -> new Exception("CourseClass dont exist")
        );
        Exams exams = new Exams();
        exams.setExamDate(request.getExamDate());
        exams.setStartTime(request.getStartDate());
        exams.setEndTime(request.getEndDate());
        exams.setRoom(request.getRoom());
        exams.setClasses(classes);
        return examsRepository.save(exams);
    }
}
