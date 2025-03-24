package com.example.student_management_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.student_management_backend.domain.Majors;

@Repository
public interface MajorsRepository extends JpaRepository<Majors, Integer>, JpaSpecificationExecutor<Majors> {
    Optional<Majors> findByMajorName(String majorName);
}