package com.example.student_management_backend.dto.reponse.grade;

import java.util.List;
import java.util.stream.Collectors;

import com.example.student_management_backend.domain.Grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponse {
    private Integer id;
    private String studentId;
    private String subjectCode;
    private String semester;
    private List<GradeDetailResponse> gradeDetails;

    public GradeResponse(Grade grade) {
        this.id = grade.getId();
        this.studentId = grade.getStudent().getId().toString();
        this.subjectCode = grade.getSubject().getSubjectCode();
        this.semester = grade.getSemester();
        this.gradeDetails = grade.getGradeDetails().stream()
                .map(GradeDetailResponse::new)
                .collect(Collectors.toList());
    }
}