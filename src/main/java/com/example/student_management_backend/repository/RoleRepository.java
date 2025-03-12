package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);

    boolean existsByRoleName(String roleName);
}