package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudent_IdAndSubject_SubjectCode(Integer studentId, String subjectCode);
}