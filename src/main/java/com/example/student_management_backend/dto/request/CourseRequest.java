package com.example.student_management_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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