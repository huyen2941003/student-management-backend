package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Students, Integer> {
    Optional<Students> findByEmail(String email);

    Students findByUserId(int userId);

    Optional<Students> findByUser(User user);

    List<Students> findAll();

}
