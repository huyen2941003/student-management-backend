package com.example.student_management_backend.dto.response;

import jakarta.persistence.criteria.CriteriaBuilder;
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
