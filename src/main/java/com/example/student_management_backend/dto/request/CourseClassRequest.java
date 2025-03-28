package com.example.student_management_backend.dto.request;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseClassRequest {
    private Integer courseId;
    private int maxStudent;

}
