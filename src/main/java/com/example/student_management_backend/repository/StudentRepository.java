package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students,Integer> {

}
