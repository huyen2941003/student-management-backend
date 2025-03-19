package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnnoucementsRepository
        extends JpaRepository<Announcements, Integer>, JpaSpecificationExecutor<Announcements> {
}
