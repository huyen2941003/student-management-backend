package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lectures, Integer> {
    Optional<Lectures> findByEmail(String email);

    Lectures findByUserId(int userId);

    Optional<Lectures> findByUser(User user);

    List<Lectures> findAll();

}
