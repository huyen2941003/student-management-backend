package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}