package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

        @Query("SELECT new com.example.student_management_backend.dto.response.ScheduleResponse(" +
                        "c.courses.name, s.date, s.startTime, s.endTime, s.room) " +
                        "FROM Schedule s " +
                        "JOIN s.courses c " +
                        "WHERE c.id IN (SELECT e.classes.id FROM Enrollments e WHERE e.students.id = :studentId) " +
                        "AND s.date BETWEEN :startDate AND :endDate " +
                        "ORDER BY s.date, s.startTime")
        List<ScheduleResponse> findByStudentIdAndDateBetween(
                        @Param("studentId") int studentId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);
}