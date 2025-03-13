package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_backend.domain.Majors;

@Repository
public interface MajorsRepository extends JpaRepository<Majors, Integer> {

}