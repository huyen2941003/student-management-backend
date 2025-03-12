package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses, Integer> {
    @Query("SELECT mh FROM Courses mh " +
            "JOIN mh.majors nh " +
            "JOIN Students u ON u.majors.id = nh.id " +
            "WHERE u.id = :userId")
    List<Courses> getCourses(int userId);
    @Query("SELECT DISTINCT c FROM Courses c JOIN c.grades g WHERE " +
            "(:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:semester IS NULL OR c.semester = :semester) " +
            "AND (:minGrade IS NULL OR g.totalScore >= :minGrade) " +
            "AND (:maxGrade IS NULL OR g.totalScore <= :maxGrade)")
    List<Courses> searchCourses(@Param("keyword") String keyword,
                               @Param("semester") String semester,
                               @Param("minGrade") Double minGrade,
                               @Param("maxGrade") Double maxGrade);
}
