package com.example.student_management_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseClassScheduleResponse {
    private String name;
    private LocalDateTime schedule;

}
