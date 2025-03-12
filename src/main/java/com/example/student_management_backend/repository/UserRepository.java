package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.User;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findById(long id);

    Optional<User> findByFcmToken(String fcmToken);

}