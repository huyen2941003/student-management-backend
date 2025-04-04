package com.example.student_management_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Exams;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseClassResponse {
    private int id;
    private int courseClassId;
    private int maxStudent;
    private int semester;
    private String courseName;
    private Integer classId;

    public CourseClassResponse(int id, int courseClassId, int semester, int maxStudent, String courseName) {
        this.id = id;
        this.courseClassId = courseClassId;
        this.semester = semester;
        this.maxStudent = maxStudent;
        this.courseName = courseName;
    }

    public CourseClassResponse(CourseClass courseClass) {
        this.id = courseClass.getId();
        this.classId = courseClass.getCourses() != null ? courseClass.getCourses().getId() : null;
        this.maxStudent = courseClass.getMaxStudent();
        this.semester = courseClass.getSemester();
        this.courseName = courseClass.getCourses() != null ? courseClass.getCourses().getName() : null;
    }
}
