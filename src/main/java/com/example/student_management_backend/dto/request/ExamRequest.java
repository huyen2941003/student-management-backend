package com.example.student_management_backend.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExamRequest {
    private LocalDate examDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String room;
    private int classesId;
}
