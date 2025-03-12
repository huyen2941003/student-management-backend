package com.example.student_management_backend.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.StudentDTO;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.repository.MajorsRepository;
import com.example.student_management_backend.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MajorsRepository majorsRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // Lấy thông tin sinh viên theo ID
    public StudentResponse getStudentById(Integer id) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));
        return new StudentResponse(student);
    }

    // Cập nhật thông tin sinh viên
    public StudentResponse updateStudent(Integer id, StudentDTO studentDTO) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));

        // Cập nhật thông tin từ DTO
        student.setFullName(studentDTO.getFullName());
        student.setDob(studentDTO.getDob());
        student.setGender(studentDTO.getGender());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());

        // Cập nhật Major nếu có thay đổi
        if (studentDTO.getMajorId() != null) {
            Majors major = majorsRepository.findById(studentDTO.getMajorId())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy chuyên ngành với id: " + studentDTO.getMajorId()));
            student.setMajors(major);
        }

        // Cập nhật Department nếu có thay đổi
        if (studentDTO.getDepartmentId() != null) {
            Departments department = departmentsRepository.findById(studentDTO.getDepartmentId())
                    .orElseThrow(
                            () -> new RuntimeException("Không tìm thấy khoa với id: " + studentDTO.getDepartmentId()));
            student.setDepartments(department);
        }

        // Lưu thông tin cập nhật vào database
        studentRepository.save(student);

        return new StudentResponse(student);
    }
}