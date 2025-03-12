package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.dto.response.GpaResponse;
import com.example.student_management_backend.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    public List<Grades> getGradesByCourseId(int courseId, int userId) {
        return gradeRepository.findByCourses_Id(courseId);
    }

    public List<Grades> getGradesByUserId(int userId) {
        return gradeRepository.findByStudentId(userId);
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
}
