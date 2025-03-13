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
        List<Grades> grades= gradeRepository.findByCourses_Id(courseId);
        return grades.stream()
                .map(GradeResponse::new)
                .toList();
    }

    public List<GradeResponse> getGradesByUserId(int userId) {
        List<Grades> grades= gradeRepository.findByStudentId(userId);
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

            double gpa = (totalCredits == 0) ? 0 : totalScore / totalCredits;  // Tránh chia cho 0
            gpaList.add(new GpaResponse(semester, gpa));
        }
        return gpaList;
    }

    public Grades createGrade(GradeRequest request) throws Exception {
        Students students =studentRepository.findById(request.getStudentId())
                .orElseThrow(
                        ()->new Exception("Student dont exist")
                );
        Courses courses =courseRepository.findById(request.getCourseId())
                .orElseThrow(
                        ()->new Exception("Course dont exist")
                );
        Grades grades = new Grades();
        grades.setMidtermScore(request.getMidtermScore());
        grades.setFinalScore(request.getFinalScore());
        double totalScore = (request.getMidtermScore() + request.getFinalScore())/2;
        String grade;
        if(totalScore>7 && totalScore<8)
        {
            grade ="A";
        } else if(totalScore>6 && totalScore<=7)
        {
            grade="B";
        }
        else if(totalScore>5 && totalScore<=6)
        {
            grade="C";
        }
        else
            grade="D";
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
                ()->new Exception("Grades dont exist")
        );
        gradeRepository.delete(grades);
    }

    public Grades updateGradeById(GradeRequest request) throws Exception {
        Grades grades = gradeRepository.findById(request.getId()).orElseThrow(
                ()->new Exception("Grades dont exist")
        );
        Students students =studentRepository.findById(request.getStudentId())
                .orElseThrow(
                        ()->new Exception("Student dont exist")
                );
        Courses courses =courseRepository.findById(request.getCourseId())
                .orElseThrow(
                        ()->new Exception("Course dont exist")
                );
        grades.setMidtermScore(request.getMidtermScore());
        grades.setFinalScore(request.getFinalScore());
        double totalScore = (request.getMidtermScore() + request.getFinalScore())/2;
        String grade;
        if(totalScore>7 && totalScore<8)
        {
            grade ="A";
        } else if(totalScore>6 && totalScore<=7)
        {
            grade="B";
        }
        else if(totalScore>5 && totalScore<=6)
        {
            grade="C";
        }
        else
            grade="D";
        grades.setTotalScore(totalScore);
        grades.setGrade(grade);
        grades.setStudents(students);
        grades.setCourses(courses);
        return gradeRepository.save(grades);
    }
}
