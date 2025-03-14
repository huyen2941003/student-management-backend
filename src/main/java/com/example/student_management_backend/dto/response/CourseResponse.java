package com.example.student_management_backend.dto.response;

import lombok.*;

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
