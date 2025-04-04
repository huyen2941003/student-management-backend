package com.example.student_management_backend.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.student_management_backend.domain.Exams;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExamsScheduleResponse {
    private Integer id;
    private LocalDate examDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String courseName;
    private String room;
    private Integer classId;

    public ExamsScheduleResponse(LocalDate examDate, LocalDateTime startTime,
            LocalDateTime endTime, String courseName, String room) {
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseName = courseName;
        this.room = room;
    }

    public ExamsScheduleResponse(Exams exam) {
        this.id = exam.getId();
        this.examDate = exam.getExamDate();
        this.startTime = exam.getStartTime();
        this.endTime = exam.getEndTime();
        this.room = exam.getRoom();
        this.courseName = exam.getClass() != null && exam.getClasses().getCourses() != null
                ? exam.getClasses().getCourses().getName()
                : null;
        this.classId = exam.getClasses() != null ? exam.getClasses().getId() : null;
    }
}
