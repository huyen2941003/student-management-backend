package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Students, Integer> {
    Optional<Students> findByEmail(String email);

    @Query(value = "SELECT s.* FROM student s JOIN user u ON s.userId = u.id WHERE u.username = :username", nativeQuery = true)
    Optional<Students> findByUsername(@Param("username") String username);

    Students findByUserId(int userId);

    Optional<Students> findByUser(User user);

    List<Students> findAll();

    boolean existsByEmail(String email);

    boolean existsByPhone(String phoneNumber);

    Optional<Students> findByUserId(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM student WHERE id = :id", nativeQuery = true)
    void hardDelete(@Param("id") Integer id);

    @Query(value = "SELECT userId FROM student WHERE id = :studentId", nativeQuery = true)
    Optional<Integer> findUserIdByStudentId(@Param("studentId") Integer studentId);

    @Modifying
    @Query(value = "DELETE FROM user WHERE id = (SELECT userId FROM student WHERE id = :studentId)", nativeQuery = true)
    void hardDeleteUserByStudentId(@Param("studentId") Integer studentId);
}
