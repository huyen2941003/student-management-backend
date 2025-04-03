package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Enrollments;
import com.example.student_management_backend.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentsRepository extends JpaRepository<Enrollments,Integer> {
    @Query(value = """
        SELECT count(*)
        FROM enrollments e
        WHERE
            e.status = :status AND e.classId = :classId
            
        """, nativeQuery = true)
    int countByClasses_IdAndStatus(Long classId, String status);

    Enrollments findByStudents_Id(Integer studentId);
    boolean existsByStudents_IdAndClasses_IdAndStatusNot(Integer studentId, Integer classId, Status status);

    @Query(value = "SELECT " +
            "s.full_name, " +
            "c.id AS classId, " +
            "g.id AS gradeId, " +
            "s.id AS studentId, " +
            "g.midterm_score, " +
            "g.final_score, " +
            "g.total_score, " +
            "g.grade, " +
            "g.score " +
            "FROM enrollments e " +
            "JOIN student s ON e.studentId = s.id " +
            "JOIN courseclass c ON e.classId = c.id " +
            "LEFT JOIN grades g ON g.studentId = s.id AND g.classId = c.id " +
            "WHERE c.id = :classId",
            nativeQuery = true)
    List<Object[]> findStudentGradesByClassId(@Param("classId") int classId);

    List<Enrollments> findByStudents_IdAndStatus(Integer studentId, Status enrolled);
}
