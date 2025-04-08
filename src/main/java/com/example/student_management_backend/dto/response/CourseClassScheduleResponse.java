package com.example.student_management_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassScheduleResponse {
    private Integer courseClassId;
    private Integer courseId;
    private String courseName;
    private Integer semester;
    private String lectureFullname;
    private Integer lectureId; // Vẫn giữ trường này, có thể set null nếu không có dữ liệu
    private Integer scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date; // Đổi tên thành startDate cho phù hợp với query mới
    private LocalDate endDate;
    private String room;
    private String classScheduleIds;
    private String classDays;
}