package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.dto.response.CourseClassResponse;
import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseClassRepository extends JpaRepository<CourseClass, Integer> {
    @Query(value = "SELECT " +
            "c.id AS courseId, " +
            "c.name AS courseName, " +
            "cc.id AS courseClassId, " +
            "c.semester AS courseSemester, " +
            "cc.max_student, " +
            "s.id AS scheduleId, " +
            "s.start_time, " +
            "s.end_time, " +
            "s.date AS startDate, " +
            "s.endDate, " +
            "s.room, " +
            "l.full_name AS lectureFullname, " +
            "GROUP_CONCAT(DISTINCT cs.classScheduleId SEPARATOR ', ') AS classScheduleIds, " +
            "GROUP_CONCAT(DISTINCT dw.dayName ORDER BY dw.id SEPARATOR ', ') AS classDays " +
            "FROM enrollments e " +
            "JOIN courseclass cc ON e.classId = cc.id " + // Hãy chắc chắn đây là khóa ngoại đúng
            "JOIN courses c ON cc.courseId = c.id " +
            "LEFT JOIN schedule s ON cc.id = s.classId " + // Hãy chắc chắn đây là khóa ngoại đúng
            "LEFT JOIN classSchedule cs ON s.id = cs.schedule_id " +
            "LEFT JOIN dayofweek dw ON cs.day_id = dw.id " +
            "LEFT JOIN lecture l ON cc.lectureId = l.id " +
            "WHERE e.studentId = :studentId " +
            "GROUP BY cc.id, s.id, c.id, c.name, c.semester, cc.max_student, s.start_time, s.end_time, s.date, s.endDate, s.room, l.full_name",
            nativeQuery = true)
    List<Object[]> getCourseSchedule(@Param("studentId") int studentId);

    @Query(value = """
            SELECT cl.id, cl.courseId,cl.max_student , cl.semester,c.name
             FROM  courseclass cl , courses c
             WHERE cl.courseId = c.id
             AND lectureId = :lectureId;
             """, nativeQuery = true)
    List<CourseClassResponse> findByLectureId(int lectureId);
}
