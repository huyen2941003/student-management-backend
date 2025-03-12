package com.example.student_management_backend.dto.response;

import com.example.student_management_backend.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private String coursesName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    public ScheduleResponse(Schedule schedule) {
        this.coursesName = schedule.getCourses().getCourses().getName() ;
        this.date = schedule.getDate();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.room = schedule.getRoom();
    }
}
