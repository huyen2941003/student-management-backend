package com.example.student_management_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.Majors;

public interface MajorsRepository extends JpaRepository<Majors, Integer> {
    Optional<Majors> findById(Integer id);

}