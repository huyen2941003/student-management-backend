package com.example.student_management_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
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
            // Kiểm tra xem departmentName mới đã tồn tại chưa
            if (departmentsRepository.existsByDepartmentName(request.getDepartmentName())) {
                throw new RuntimeException("Departmentname đã tồn tại!");
            }
            // Cập nhật departmentName nếu nó thay đổi
            department.setDepartmentName(request.getDepartmentName());
        }

        // Cập nhật description
        department.setDescription(request.getDescription());

        // Lưu department vào cơ sở dữ liệu
        department = departmentsRepository.save(department);

        // Trả về response
        return new DepartmentsResponse(department.getId(), department.getDepartmentName(), department.getDescription());
    }

    public void deleteDepartment(Integer id) {
        departmentsRepository.deleteById(id);
    }
}