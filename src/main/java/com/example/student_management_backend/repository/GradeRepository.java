package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grades, Integer>, JpaSpecificationExecutor<Grades> {


    List<Grades> findByCoursesClass_Id(int courseId);

    @Query("SELECT g FROM Grades g WHERE g.students.id = :studentId")
    List<Grades> findByStudentId(int studentId);

    @Query("SELECT c.semester, SUM(g.score * co.credit) / SUM(co.credit) " +
            "FROM Grades g " +
            "JOIN g.students s " +
            "JOIN Enrollments e ON s.id = e.students.id " +
            "JOIN CourseClass c ON e.classes.id = c.id " +
            "JOIN Courses co ON c.courses.id = co.id " +
            "WHERE s.id = :studentId " +
            "GROUP BY c.semester " +
            "ORDER BY c.semester")
    List<Object[]> getRawGpaBySemester(int studentId);

    @Query(value = """
        SELECT SUM(s.score * c2.credit) / SUM(c2.credit) AS total_gpa
        FROM grades s
        JOIN courseclass c ON s.classId  = c.id
        JOIN courses c2 on c.courseId = c2.id
        WHERE s.studentId = :studentId;
        """, nativeQuery = true)
    Double calculateTotalGPA( Integer studentId);

    @Query(value = """
            SELECT c.semester, SUM(s.score * c2.credit) / SUM(c2.credit)
            FROM grades s
            JOIN courseclass c ON s.classId  = c.id
            JOIN courses c2 on c.courseId = c2.id
            WHERE  s.studentId = :studentId
            GROUP BY c.semester ORDER BY c.semester;
            """, nativeQuery = true)
    List<Object[]> calculateGPABySemester(Integer studentId);
}
