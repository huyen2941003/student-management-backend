package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.dto.response.GpaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grades,Integer> {

    List<Grades> findByCourses_Id(int courseId);
    @Query("SELECT g FROM Grades g WHERE g.students.id = :studentId")
    List<Grades> findByStudentId(int studentId);
    @Query("SELECT c.semester, SUM(g.totalScore * co.credit), SUM(co.credit) " +
            "FROM Grades g " +
            "JOIN g.students s " +
            "JOIN Enrollments e ON s.id = e.students.id " +
            "JOIN CourseClass c ON e.classes.id = c.id " +
            "JOIN Courses co ON c.courses.id = co.id " +
            "WHERE s.id = :studentId " +
            "GROUP BY c.semester " +
            "ORDER BY c.semester")
    List<Object[]> getRawGpaBySemester(int studentId);

}
