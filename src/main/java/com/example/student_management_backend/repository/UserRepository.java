package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.student_management_backend.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findById(long id);

    Optional<User> findByFcmToken(String fcmToken);

    Optional<User> findByResetToken(String resetToken);

    List<User> findByUsernameContainingIgnoreCase(String keyword);

    @Modifying
    @Query(value = "DELETE FROM user WHERE id = :userId", nativeQuery = true)
    void hardDeleteUser(@Param("userId") Integer userId);

}