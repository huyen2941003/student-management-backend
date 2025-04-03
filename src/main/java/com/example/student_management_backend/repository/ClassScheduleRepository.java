package com.example.student_management_backend.repository;

import com.example.student_management_backend.domain.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule,Integer> {
    List<ClassSchedule> findByScheduleId(Integer id);
}
