package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
