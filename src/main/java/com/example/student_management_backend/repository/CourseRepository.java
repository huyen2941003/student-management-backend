package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses, Integer> {
    @Query("SELECT mh FROM Courses mh " +
            "JOIN mh.majors nh " +
            "JOIN Students u ON u.majors.id = nh.id " +
            "WHERE u.id = :userId")
    List<Courses> getCourses(int userId);

}
