package com.example.student_management_backend.dto.reponse.schedule;

import java.time.LocalDateTime;

import com.example.student_management_backend.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private Integer id;
    private String subjectCode;
    private String classroom;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String dayOfWeek;

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.subjectCode = schedule.getSubject().getSubjectCode();
        this.classroom = schedule.getClassroom();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.dayOfWeek = schedule.getDayOfWeek();
    }

    // Getters and Setters
}