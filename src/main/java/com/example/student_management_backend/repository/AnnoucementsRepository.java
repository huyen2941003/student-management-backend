package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Announcements;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnoucementsRepository extends JpaRepository<Announcements, Integer> {

    // tìm bằng tiêu đề hoặc nội dung
    List<Announcements> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

}
