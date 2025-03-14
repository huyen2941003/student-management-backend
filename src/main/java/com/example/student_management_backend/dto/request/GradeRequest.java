package com.example.student_management_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GradeRequest {
    private int id;
    private double midtermScore;
    private double finalScore;
    private int courseId;
    private int studentId;
}
