package com.example.student_management_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

@NoArgsConstructor
public class ScheduleResponse {
    private String courseName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private Integer lectureId;
    private LocalDate endDate;
    private Long classScheduleId;
    private Long dayOfWeekId;
    private String dayName;

    public ScheduleResponse(String courseName, LocalDate date, LocalTime startTime, LocalTime endTime,
                            String room, Integer lectureId, LocalDate endDate) {
        this.courseName = courseName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.lectureId = lectureId;
        this.endDate = endDate;

    }

    // Constructor với 10 tham số (cho các phương thức mới)
    public ScheduleResponse(String courseName, LocalDate date, LocalTime startTime, LocalTime endTime,
                            String room, Integer lectureId, LocalDate endDate, Long classScheduleId,
                            Long dayOfWeekId, String dayName) {
        this.courseName = courseName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.lectureId = lectureId;
        this.endDate = endDate;
        this.classScheduleId = classScheduleId;
        this.dayOfWeekId = dayOfWeekId;
        this.dayName = dayName;
    }
}