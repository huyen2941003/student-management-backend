package com.example.student_management_backend.repository;

import java.util.Optional;

import com.example.student_management_backend.domain.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository extends JpaRepository<Departments, Integer> {
    Optional<Departments> findById(Integer id);
}
