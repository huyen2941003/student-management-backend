package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.dto.response.CourseClassResponse;
import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseClassRepository extends JpaRepository<CourseClass, Integer> {
    @Query("SELECT new com.example.student_management_backend.dto.response.CourseClassScheduleResponse(e.courses.name, e.createdDate) "
            +
            "FROM CourseClass e " +
            "JOIN e.courses cl " +
            "WHERE e.id IN (SELECT en.classes.id FROM Enrollments en WHERE en.students.id = :userId) ")

    // tìm lớp học bằng userId người dùng
    List<CourseClassScheduleResponse> getCourseChedule(int userId);

    @Query(value = """
       SELECT cl.id, cl.courseId,cl.max_student , cl.semester,c.name
        FROM  courseclass cl , courses c
        WHERE cl.courseId = c.id
        AND lectureId = :lectureId;
        """ , nativeQuery = true)
    List<CourseClassResponse> findByLectureId(int lectureId);
}
