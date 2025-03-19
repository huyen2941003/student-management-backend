package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.ExamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            exams.setClasses(classes);

            return examsRepository.save(exams);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create exam: " + e.getMessage(), e);
        }
    }

    public Page<Exams> searchExams(
            LocalDate examDate,
            LocalDate examDateFrom,
            LocalDate examDateTo,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String room,
            String courseName,
            Integer classId,
            Pageable pageable) {

        Specification<Exams> spec = Specification.where(null);

        // Tìm kiếm theo ngày thi cụ thể
        if (examDate != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("examDate"), examDate));
        }

        // Tìm kiếm theo khoảng ngày thi
        if (examDateFrom != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("examDate"),
                    examDateFrom));
        }
        if (examDateTo != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("examDate"),
                    examDateTo));
        }

        // Tìm kiếm theo thời gian bắt đầu
        if (startTime != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"), startTime));
        }

        // Tìm kiếm theo thời gian kết thúc
        if (endTime != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endTime"), endTime));
        }

        // Tìm kiếm theo phòng thi
        if (room != null && !room.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("room"), "%" + room + "%"));
        }

        // Tìm kiếm theo tên môn học
        if (courseName != null && !courseName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder
                    .like(root.get("classes").get("courses").get("name"), "%" + courseName + "%"));
        }

        // Tìm kiếm theo ID lớp học
        if (classId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("classes").get("id"), classId));
        }

        return examsRepository.findAll(spec, pageable);
    }
}
