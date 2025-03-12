package com.example.student_management_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseRequest {
    private String name;
    private int credit;
    private int semester;
    private int majorId;
}
