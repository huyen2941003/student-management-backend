package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Enrollments;
import com.example.student_management_backend.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
