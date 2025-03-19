package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.request.GradeRequest;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.dto.response.GradeResponse;
import com.example.student_management_backend.repository.CourseRepository;
import com.example.student_management_backend.repository.GradeRepository;
import com.example.student_management_backend.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public List<GradeResponse> getGradesByCourseId(int courseId, int userId) {
        List<Grades> grades = gradeRepository.findByCourses_Id(courseId);
        return grades.stream()
                .map(GradeResponse::new)
                .toList();
    }

    public List<GradeResponse> getGradesByUserId(int userId) {
        List<Grades> grades = gradeRepository.findByStudentId(userId);
        return grades.stream()
                .map(GradeResponse::new)
                .toList();
    }

    public List<GpaResponse> calculateGpaBySemester(int studentId) {
        List<Object[]> results = gradeRepository.getRawGpaBySemester(studentId);
        List<GpaResponse> gpaList = new ArrayList<>();

        for (Object[] row : results) {
            Integer semester = (Integer) row[0];
            double totalScore = ((Number) row[1]).doubleValue();
            double totalCredits = ((Number) row[2]).doubleValue();

            double gpa = (totalCredits == 0) ? 0 : totalScore / totalCredits;
            gpaList.add(new GpaResponse(semester, gpa));
        }
        return gpaList;
    }

    public Grades createGrade(GradeRequest request) throws Exception {
        Students students = studentRepository.findById(request.getStudentId())
                .orElseThrow(
                        () -> new Exception("Student dont exist"));
        Courses courses = courseRepository.findById(request.getCourseId())
                .orElseThrow(
                        () -> new Exception("Course dont exist"));
        Grades grades = new Grades();
        grades.setMidtermScore(request.getMidtermScore());
        grades.setFinalScore(request.getFinalScore());
        double totalScore = (request.getMidtermScore() + request.getFinalScore()) / 2;
        String grade;
        if (totalScore >= 8.5) {
            grade = "A";
        } else if (totalScore >= 7.0 && totalScore < 8.5) {
            grade = "B";
        } else if (totalScore >= 5.5 && totalScore <= 6.9) {
            grade = "C";
        } else if (totalScore >= 4.0 && totalScore <= 5.4) {
            grade = "D";
        } else
            grade = "E";
        grades.setTotalScore(totalScore);
        grades.setGrade(grade);
        grades.setStudents(students);
        grades.setCourses(courses);
        return gradeRepository.save(grades);
    }

    public List<Grades> getAllGrades() {
        return gradeRepository.findAll();
    }

    public void deleteGradeById(int id) throws Exception {
        Grades grades = gradeRepository.findById(id).orElseThrow(
                () -> new Exception("Grades dont exist"));
        gradeRepository.delete(grades);
    }

    public Grades updateGradeById(GradeRequest request) throws Exception {
        Grades grades = gradeRepository.findById(request.getId()).orElseThrow(
                () -> new Exception("Grades dont exist"));
        Students students = studentRepository.findById(request.getStudentId())
                .orElseThrow(
                        () -> new Exception("Student dont exist"));
        Courses courses = courseRepository.findById(request.getCourseId())
                .orElseThrow(
                        () -> new Exception("Course dont exist"));
        grades.setMidtermScore(request.getMidtermScore());
        grades.setFinalScore(request.getFinalScore());
        double totalScore = (request.getMidtermScore() + request.getFinalScore()) / 2;
        String grade;
        if (totalScore >= 8.5) {
            grade = "A";
        } else if (totalScore >= 7.0 && totalScore < 8.5) {
            grade = "B";
        } else if (totalScore >= 5.5 && totalScore <= 6.9) {
            grade = "C";
        } else if (totalScore >= 4.0 && totalScore <= 5.4) {
            grade = "D";
        } else
            grade = "E";
        grades.setTotalScore(totalScore);
        grades.setGrade(grade);
        grades.setStudents(students);
        grades.setCourses(courses);
        return gradeRepository.save(grades);
    }

    public Page<Grades> searchGrades(
            Double midtermScore, Double midtermScoreMin, Double midtermScoreMax,
            Double finalScore, Double finalScoreMin, Double finalScoreMax,
            Double totalScore, Double totalScoreMin, Double totalScoreMax,
            String semester, Integer studentId, Integer courseId,
            Pageable pageable) {

        // Tạo một Specification ban đầu
        Specification<Grades> spec = Specification.where(null);

        // Tìm kiếm theo khoảng giá trị của midtermScore
        if (midtermScore != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("midtermScore"), midtermScore));
        } else if (midtermScoreMin != null && midtermScoreMax != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("midtermScore"),
                    midtermScoreMin, midtermScoreMax));
        }

        // Tìm kiếm theo khoảng giá trị của finalScore
        if (finalScore != null) {
            spec = spec
                    .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("finalScore"), finalScore));
        } else if (finalScoreMin != null && finalScoreMax != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("finalScore"),
                    finalScoreMin, finalScoreMax));
        }

        // Tìm kiếm theo khoảng giá trị của totalScore
        if (totalScore != null) {
            spec = spec
                    .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("totalScore"), totalScore));
        } else if (totalScoreMin != null && totalScoreMax != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("totalScore"),
                    totalScoreMin, totalScoreMax));
        }

        // Tìm kiếm theo semester
        if (semester != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("semester"), "%" + semester + "%"));
        }

        // Tìm kiếm theo studentId
        if (studentId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("students").get("id"), studentId));
        }

        // Tìm kiếm theo courseId
        if (courseId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courses").get("id"), courseId));
        }

        // Thực hiện truy vấn với phân trang và sắp xếp
        return gradeRepository.findAll(spec, pageable);
    }

}
