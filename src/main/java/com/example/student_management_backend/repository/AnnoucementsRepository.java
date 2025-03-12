package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnoucementsRepository extends JpaRepository<Announcements, Integer > {

}
