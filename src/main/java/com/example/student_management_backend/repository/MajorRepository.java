package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Majors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Majors, Integer> {
}
