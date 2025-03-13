package com.example.student_management_backend.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ScheduleCourseRequest {
    private int coursesId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String room;
}
