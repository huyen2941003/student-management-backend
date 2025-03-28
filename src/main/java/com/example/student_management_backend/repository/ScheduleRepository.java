package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

    @Query(value = """
        SELECT 
            c2.name AS courseName, 
            s.date AS date, 
            s.start_time AS startTime, 
            s.end_time AS endTime, 
            s.room AS room,
            c.lectureId AS lectureId
        FROM schedule s
        JOIN courseclass c ON s.classId = c.id
        JOIN courses c2 on c.courseId = c2.id
        WHERE c.id IN (
            SELECT e.classId FROM enrollments e WHERE e.studentId = :studentId
        )
        AND s.date BETWEEN :startDate AND :endDate
        ORDER BY s.date, s.start_time
        """, nativeQuery = true)
    List<Object[]> getScheduleByStudentId(
            @Param("studentId") Integer studentId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    @Query(value = """
        SELECT 
            c2.name AS courseName, 
            s.date AS date, 
            s.start_time AS startTime, 
            s.end_time AS endTime, 
            s.room AS room,
            c.lectureId AS lectureId
        FROM schedule s
        JOIN courseclass c ON s.classId = c.id
        JOIN courses c2 on c.courseId = c2.id
        WHERE c.lectureId = :lectureId
        AND s.date BETWEEN :startDate AND :endDate
        ORDER BY s.date, s.start_time
        """, nativeQuery = true)
    List<Object[]> getScheduleByLectureId(Integer lectureId, LocalDate startDate, LocalDate endDate);
}