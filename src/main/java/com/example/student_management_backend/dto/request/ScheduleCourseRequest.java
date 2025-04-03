package com.example.student_management_backend.dto.request;

import com.example.student_management_backend.domain.DayOfWeek;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private List<DayOfWeek.DayName> daysOfWeek;
    private LocalDate endDate;
}
