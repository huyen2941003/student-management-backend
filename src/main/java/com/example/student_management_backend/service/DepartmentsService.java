package com.example.student_management_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.dto.request.DepartmentsRequest;
import com.example.student_management_backend.dto.response.department.DepartmentsResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentsService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    public DepartmentsResponse createDepartment(DepartmentsRequest request) {

        if (departmentsRepository.existsByDepartmentName(request.getDepartmentName())) {
            throw new RuntimeException("Departmentname đã tồn tại!");
        }

        Departments department = new Departments();
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());
        department = departmentsRepository.save(department);
        return new DepartmentsResponse(department.getId(), department.getDepartmentName(), department.getDescription());
    }

    public List<DepartmentsResponse> getAllDepartments() {
        return departmentsRepository.findAll().stream()
                .map(department -> new DepartmentsResponse(department.getId(), department.getDepartmentName(),
                        department.getDescription()))
                .collect(Collectors.toList());
    }

    public DepartmentsResponse getDepartmentById(Integer id) {
        Departments department = departmentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("không tìm thấy Department"));
        return new DepartmentsResponse(department.getId(), department.getDepartmentName(), department.getDescription());
    }

    public DepartmentsResponse updateDepartment(Integer id, DepartmentsRequest request) {
        Departments department = departmentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Department"));

        if (!department.getDepartmentName().equals(request.getDepartmentName())) {
            if (departmentsRepository.existsByDepartmentName(request.getDepartmentName())) {
                throw new RuntimeException("Departmentname đã tồn tại!");
            }
            department.setDepartmentName(request.getDepartmentName());
        }

        department.setDescription(request.getDescription());

        department = departmentsRepository.save(department);

        return new DepartmentsResponse(department.getId(), department.getDepartmentName(), department.getDescription());
    }

    public void deleteDepartment(Integer id) {
        departmentsRepository.deleteById(id);
    }

    public List<Departments> searchDepartments(String departmentName, String description) {
        Specification<Departments> spec = Specification.where(null);

        if (departmentName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("departmentName"),
                    "%" + departmentName + "%"));
        }
        if (description != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"),
                    "%" + description + "%"));
        }

        return departmentsRepository.findAll(spec);
    }
}