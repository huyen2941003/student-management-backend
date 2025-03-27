package com.example.student_management_backend.dto.response;

import com.example.student_management_backend.domain.Grades;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GradeResponse {
    private Integer id;
    private double midtermScore;
    private double finalScore;
    private double totalScore;
    private String grade;
    int studentsId;
    String fullName;

    int courseClassId;
    String courseName;

    public GradeResponse(Grades grades) {
        this.id = grades.getId();
        this.midtermScore = grades.getMidtermScore();
        this.finalScore = grades.getFinalScore();
        this.totalScore = grades.getTotalScore();
        this.grade = grades.getGrade();
        this.studentsId = grades.getStudents().getId();
        this.fullName = grades.getStudents().getFullName();
        this.courseClassId = grades.getCoursesClass().getId();
        this.courseName = grades.getCoursesClass().getCourses().getName();
    }
}
