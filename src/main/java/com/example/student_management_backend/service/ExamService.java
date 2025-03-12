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
                    () -> new Exception("Exam dont exist"));
            examsRepository.delete(exams);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Exams createExam(ExamRequest request) {
        try {
            CourseClass classes = courseClassRepository.findById(request.getClassesId())
                    .orElseThrow(() -> new Exception("CourseClass don't exist"));

            Exams exams = new Exams();
            exams.setExamDate(request.getExamDate());
            exams.setStartTime(request.getStartDate());
            exams.setClasses(classes); // Thiết lập lớp học phần cho kỳ thi

            return examsRepository.save(exams); // Lưu kỳ thi vào database và trả về
        } catch (Exception e) {
            // Xử lý ngoại lệ (ví dụ: ghi log hoặc ném một ngoại lệ tùy chỉnh)
            throw new RuntimeException("Failed to create exam: " + e.getMessage(), e);
        }
    }
}
