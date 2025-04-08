package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Lectures;
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
public interface LectureRepository extends JpaRepository<Lectures, Integer> {
    Optional<Lectures> findByEmail(String email);

    @Query(value = "SELECT l.* FROM lecture l JOIN user u ON l.userId = u.id WHERE u.username = :username", nativeQuery = true)
    Optional<Lectures> findByUsername(@Param("username") String username);

    Lectures findByUserId(Long userId);

    Optional<Lectures> findByUser(User user);

    List<Lectures> findAll();

    boolean existsByEmail(String email);

    boolean existsByPhone(String phoneNumber);

    Integer getLectureByUserId(Long userId);

    Lectures findByUserId(int userId);

    Optional<Lectures> findByUserId(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM lecture WHERE id = :id", nativeQuery = true)
    void hardDelete(@Param("id") Integer id);

    @Query(value = "SELECT userId FROM lecture WHERE id = :lectureId", nativeQuery = true)
    Optional<Integer> findUserIdByLectureId(@Param("lectureId") Integer lectureId);

    @Modifying
    @Query(value = "DELETE FROM user WHERE id = (SELECT userId FROM lecture WHERE id = :lectureId)", nativeQuery = true)
    void hardDeleteUserByLectureId(@Param("lectureId") Integer lectureId);

}
