package com.example.student_management_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.dto.request.MajorsRequest;
import com.example.student_management_backend.dto.response.major.MajorsResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.repository.MajorsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MajorsService {

    @Autowired
    private MajorsRepository majorsRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    public MajorsResponse createMajor(MajorsRequest request) {
        Departments department = departmentsRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Majors major = new Majors();
        major.setMajorName(request.getMajorName());
        major.setDepartments(department);
        major = majorsRepository.save(major);
        return new MajorsResponse(major.getId(), major.getMajorName(), major.getDepartments().getId());
    }

    public List<MajorsResponse> getAllMajors() {
        return majorsRepository.findAll().stream()
                .map(major -> new MajorsResponse(major.getId(), major.getMajorName(), major.getDepartments().getId()))
                .collect(Collectors.toList());
    }

    public MajorsResponse getMajorById(Integer id) {
        Majors major = majorsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found"));
        return new MajorsResponse(major.getId(), major.getMajorName(), major.getDepartments().getId());
    }

    public MajorsResponse updateMajor(Integer id, MajorsRequest request) {
        Majors major = majorsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found"));
        Departments department = departmentsRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        major.setMajorName(request.getMajorName());
        major.setDepartments(department);
        major = majorsRepository.save(major);
        return new MajorsResponse(major.getId(), major.getMajorName(), major.getDepartments().getId());
    }

    public void deleteMajor(Integer id) {
        majorsRepository.deleteById(id);
    }

    public List<Majors> searchMajors(String majorName, String departmentName) {
        Specification<Majors> spec = Specification.where(null);

        if (majorName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("majorName"),
                    "%" + majorName + "%"));
        }

        if (departmentName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder
                    .like(root.get("departments").get("departmentName"), "%" + departmentName + "%"));
        }

        return majorsRepository.findAll(spec);
    }
}