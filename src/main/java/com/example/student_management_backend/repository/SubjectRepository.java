package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.Subject;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findBySubjectNameContainingIgnoreCase(String subjectName);
}