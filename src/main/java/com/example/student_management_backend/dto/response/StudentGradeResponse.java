package com.example.student_management_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentGradeResponse {
    private String fullName;
    private Integer classId;
    private Integer gradeId;
    private Integer studentId;
    private Double midtermScore;
    private Double finalScore;
    private Double totalScore;
    private String grade;
    private Double score;
}

