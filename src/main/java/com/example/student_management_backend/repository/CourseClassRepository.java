package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseClassRepository extends JpaRepository<CourseClass,Integer> {
    @Query("SELECT new com.example.student_management_backend.dto.response.CourseClassScheduleResponse(e.courses.name, e.createdDate) " +
            "FROM CourseClass e " +
            "JOIN e.courses cl " +
            "WHERE e.id IN (SELECT en.classes.id FROM Enrollments en WHERE en.students.id = :userId) "
            )
    List<CourseClassScheduleResponse> getCourseChedule(int userId);
}
