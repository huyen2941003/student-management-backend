package com.example.student_management_backend.repository;

import java.util.Optional;

import com.example.student_management_backend.domain.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepository
        extends JpaRepository<Departments, Integer>, JpaSpecificationExecutor<Departments> {
    Optional<Departments> findById(Integer id);

    boolean existsByDepartmentName(String departmentName);

    Optional<Departments> findByDepartmentName(String departmentName); // Tìm Departments theo departmentName
}
