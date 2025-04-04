package com.example.student_management_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data

@NoArgsConstructor
public class ScheduleResponse {
    private Integer id;
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
    private List<String> dayNames;
    private String fullName;
    private Integer courseClassId;
    public ScheduleResponse(Integer id, String courseName, LocalDate date, LocalTime startTime, LocalTime endTime,
                            String room, Integer lectureId, String fullName, LocalDate endDate, Long classScheduleId,
                            Long dayOfWeekId, List<String> dayNames, Integer courseClassId) {
        this.id = id;
        this.courseName = courseName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.lectureId = lectureId;
        this.fullName = fullName;
        this.endDate = endDate;
        this.classScheduleId = classScheduleId;
        this.dayOfWeekId = dayOfWeekId;
        this.dayNames = dayNames;
        this.courseClassId = courseClassId;
    }

    // Constructor với 10 tham số (cho các phương thức mới)

    public ScheduleResponse(Integer id, String courseName, LocalDate date, LocalTime startTime, LocalTime endTime,
                            String room, Integer lectureId,String fullName, LocalDate endDate, Long classScheduleId,
                            Long dayOfWeekId, String dayName) {
        this.id = id;
        this.courseName = courseName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.lectureId = lectureId;
        this.fullName = fullName;
        this.endDate = endDate;
        this.classScheduleId = classScheduleId;
        this.dayOfWeekId = dayOfWeekId;
        this.dayName = dayName;
    }

    public ScheduleResponse(String name, LocalDate date, LocalTime startTime, LocalTime endTime, String room, Integer lectureId, LocalDate endDate) {
        this.courseName = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.lectureId = lectureId;
        this.endDate = endDate;
    }
}