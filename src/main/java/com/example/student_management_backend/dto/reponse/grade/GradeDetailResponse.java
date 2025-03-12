package com.example.student_management_backend.dto.reponse.grade;

import com.example.student_management_backend.domain.GradeDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDetailResponse {
    private Integer id;
    private Double score;
    private String gradeType;

    public GradeDetailResponse(GradeDetail gradeDetail) {
        this.id = gradeDetail.getId();
        this.score = gradeDetail.getScore();
        this.gradeType = gradeDetail.getGradeType();
    }
}
