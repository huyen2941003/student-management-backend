package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_backend.domain.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findBySubject_SubjectCode(String subjectCode);
}