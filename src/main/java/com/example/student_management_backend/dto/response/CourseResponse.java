package com.example.student_management_backend.dto.response;

import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.domain.Schedule;
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
