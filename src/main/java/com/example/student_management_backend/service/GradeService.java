package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.request.GradeRequest;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.dto.response.GradeResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;

    public List<GradeResponse> getGradesByCourseId(int courseId, int userId) {
        List<Grades> grades = gradeRepository.findByCoursesClass_Id(courseId);
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
        System.out.println("Found student: " + students);
        CourseClass courseClass = courseClassRepository.findById(request.getCourseClassId())
                .orElseThrow(
                        () -> new Exception("CourseClass dont exist"));
        Grades grades = new Grades();
        grades.setMidtermScore(request.getMidtermScore());
        grades.setFinalScore(request.getFinalScore());
        double totalScore = (request.getMidtermScore()*0.3 + request.getFinalScore()*0.7);
        String grade;
        if (totalScore >= 8.5) {
            grade = "A";
            grades.setScore(4.0);
        } else if (totalScore >= 7.8 && totalScore < 8.5) {
            grade = "B+";
            grades.setScore(3.5);
        } else if (totalScore >= 7.0 && totalScore < 7.8) {
            grade = "B";
            grades.setScore(3.0);
        } else if (totalScore >= 6.5 && totalScore <= 6.9) {
            grade = "C+";
            grades.setScore(2.5);
        } else if (totalScore >= 5.5 && totalScore <= 6.4) {
            grade = "C";
            grades.setScore(2.0);
        } else if (totalScore >= 5.0 && totalScore <= 5.4) {
            grade = "D+";
            grades.setScore(1.5);
        } else if (totalScore >= 4.0 && totalScore <= 4.9) {
            grade = "D";
            grades.setScore(1.0);
        }else
        {
            grades.setScore(0.0);
            grade = "F";
        }
        grades.setTotalScore(totalScore);
        grades.setGrade(grade);
        grades.setStudents(students);
        grades.setCoursesClass(courseClass);
        grades.setSemester(String.valueOf(courseClass.getSemester()));
        return gradeRepository.save(grades);
    }
//
//    public List<Grades> getAllGrades() {
//        return gradeRepository.findAll();
//    }
//
//    public void deleteGradeById(int id) throws Exception {
//        Grades grades = gradeRepository.findById(id).orElseThrow(
//                () -> new Exception("Grades dont exist"));
//        gradeRepository.delete(grades);
//    }
//
    public Grades updateGradeById(GradeRequest request) throws Exception {
        Grades grades = gradeRepository.findById(request.getId()).orElseThrow(
                () -> new Exception("Grades dont exist"));
        Students students = studentRepository.findById(request.getStudentId())
                .orElseThrow(
                        () -> new Exception("Student dont exist"));
        CourseClass courseClass = courseClassRepository.findById(request.getCourseClassId())
                .orElseThrow(
                        () -> new Exception("CourseClass dont exist"));
        // Chỉ cập nhật nếu giá trị mới khác null
        if (request.getMidtermScore()!= null) {
            grades.setMidtermScore(request.getMidtermScore());
        }
        if (request.getFinalScore() != null) {
            grades.setFinalScore(request.getFinalScore());
        }
        double midtermScore = grades.getMidtermScore();
        double finalScore = grades.getFinalScore();
        double totalScore = (midtermScore*0.3 + finalScore*0.7);
        String grade;
        if (totalScore >= 8.5) {
            grade = "A";
            grades.setScore(4.0);
        } else if (totalScore >= 7.8 && totalScore < 8.5) {
            grade = "B+";
            grades.setScore(3.5);
        } else if (totalScore >= 7.0 && totalScore < 7.8) {
            grade = "B";
            grades.setScore(3.0);
        } else if (totalScore >= 6.5 && totalScore <= 6.9) {
            grade = "C+";
            grades.setScore(2.5);
        } else if (totalScore >= 5.5 && totalScore <= 6.4) {
            grade = "C";
            grades.setScore(2.0);
        } else if (totalScore >= 5.0 && totalScore <= 5.4) {
            grade = "D+";
            grades.setScore(1.5);
        } else if (totalScore >= 4.0 && totalScore <= 4.9) {
            grade = "D";
            grades.setScore(1.0);
        }else
        {
            grades.setScore(0.0);
            grade = "F";
        }

        grades.setTotalScore(totalScore);
        grades.setGrade(grade);
        grades.setStudents(students);
        grades.setCoursesClass(courseClass);
        return gradeRepository.save(grades);
    }

//    public Page<Grades> searchGrades(
//            Double midtermScore, Double midtermScoreMin, Double midtermScoreMax,
//            Double finalScore, Double finalScoreMin, Double finalScoreMax,
//            Double totalScore, Double totalScoreMin, Double totalScoreMax,
//            String semester, Integer studentId, Integer courseId,
//            Pageable pageable) {
//
//        Specification<Grades> spec = Specification.where(null);
//
//        // Tìm kiếm theo khoảng giá trị của midtermScore
//        if (midtermScore != null) {
//            spec = spec.and(
//                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("midtermScore"), midtermScore));
//        } else if (midtermScoreMin != null && midtermScoreMax != null) {
//            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("midtermScore"),
//                    midtermScoreMin, midtermScoreMax));
//        }
//
//        // Tìm kiếm theo khoảng giá trị của finalScore
//        if (finalScore != null) {
//            spec = spec
//                    .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("finalScore"), finalScore));
//        } else if (finalScoreMin != null && finalScoreMax != null) {
//            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("finalScore"),
//                    finalScoreMin, finalScoreMax));
//        }
//
//        // Tìm kiếm theo khoảng giá trị của totalScore
//        if (totalScore != null) {
//            spec = spec
//                    .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("totalScore"), totalScore));
//        } else if (totalScoreMin != null && totalScoreMax != null) {
//            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("totalScore"),
//                    totalScoreMin, totalScoreMax));
//        }
//
//        // Tìm kiếm theo semester
//        if (semester != null) {
//            spec = spec.and(
//                    (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("semester"), "%" + semester + "%"));
//        }
//
//        // Tìm kiếm theo studentId
//        if (studentId != null) {
//            spec = spec.and(
//                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("students").get("id"), studentId));
//        }
//
//        // Tìm kiếm theo courseId
//        if (courseId != null) {
//            spec = spec.and(
//                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courses").get("id"), courseId));
//        }
//
//        // Thực hiện truy vấn với phân trang và sắp xếp
//        return gradeRepository.findAll(spec, pageable);
//    }
    public Double getTotalGPA(Integer studentId) {
        return gradeRepository.calculateTotalGPA(studentId);
    }
    //totalbySemester
    public Map<Integer, Double> getGPABySemester(Integer studentId) {
        List<Object[]> results = gradeRepository.calculateGPABySemester(studentId);
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],  // Semester
                        row -> (Double) row[1]    // GPA
                ));
    }
}
