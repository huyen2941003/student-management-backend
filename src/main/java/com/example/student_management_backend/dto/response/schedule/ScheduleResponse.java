package com.example.student_management_backend.dto.response.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.student_management_backend.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Integer id;
    private Integer classId; // ID của lớp học phần
    private String courseName; // Tên khóa học (lấy từ Courses)
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    // Constructor để chuyển đổi từ Schedule sang ScheduleResponse
    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.classId = schedule.getCourses().getId(); // Lấy ID của lớp học phần
        this.courseName = schedule.getCourses().getCourses().getName(); // Lấy tên khóa học từ Courses
        this.date = schedule.getDate();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.room = schedule.getRoom();
    }
}