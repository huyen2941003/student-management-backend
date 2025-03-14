package com.example.student_management_backend.dto.response;

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
public class CourseResponse {
    private int id;
    String name;
    int credit;
    int semester;
    String majorName;

}