package com.example.student_management_backend.repository;


import com.example.student_management_backend.domain.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Long> {
    Optional<DayOfWeek> findByDayName(DayOfWeek.DayName dayName);
}