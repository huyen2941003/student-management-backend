package com.example.student_management_backend.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExamsScheduleResponse {
    private LocalDate examDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String courseName;
    private String room;
}
