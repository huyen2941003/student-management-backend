package com.example.student_management_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseClassResponse {
    private int id;
    private int courseClassId;
    private int maxStudent;
    private int semester;
    private String courseName;
}
