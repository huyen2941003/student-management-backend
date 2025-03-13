package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.response.ExamsScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExamsRepository extends JpaRepository<Exams, Integer> {
    @Query("SELECT new com.example.student_management_backend.dto.response.ExamsScheduleResponse(e.examDate, e.startTime, e.endTime, c.name, e.room) "
            +
            "FROM Exams e " +
            "JOIN e.classes cl " +
            "JOIN cl.courses c " +
            "WHERE cl.id IN (SELECT en.classes.id FROM Enrollments en WHERE en.students.id = :userId) " +
            "ORDER BY e.examDate, e.startTime")
    List<ExamsScheduleResponse> getExams(int userId);

    // tìm lịch thi bằng ngày hoặc tên môn thi
    List<Exams> findByClasses_Courses_NameContainingIgnoreCaseAndExamDate(String keyword, LocalDate date);

}
