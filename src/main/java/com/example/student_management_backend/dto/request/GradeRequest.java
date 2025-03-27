package com.example.student_management_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GradeRequest {
    private int id;
    private Double  midtermScore;
    private Double  finalScore;
    private int courseClassId;
    private int studentId;
}
