package com.example.student_management_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.request.StudentRequest;
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
    public StudentResponse updateStudent(Integer id, StudentRequest studentRequest) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));

        // Cập nhật thông tin từ DTO
        student.setFullName(studentRequest.getFullName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());

        // Cập nhật Major nếu có thay đổi
        if (studentRequest.getMajorId() != null) {
            Majors major = majorsRepository.findById(studentRequest.getMajorId())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy chuyên ngành với id: " + studentRequest.getMajorId()));
            student.setMajors(major);
        }

        // Cập nhật Department nếu có thay đổi
        if (studentRequest.getDepartmentId() != null) {
            Departments department = departmentsRepository.findById(studentRequest.getDepartmentId())
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Không tìm thấy khoa với id: " + studentRequest.getDepartmentId()));
            student.setDepartments(department);
        }

        // Lưu thông tin cập nhật vào database
        studentRepository.save(student);

        return new StudentResponse(student);
    }
    public Students getStudentByUserId(int userId)
    {
        return studentRepository.findByUserId(userId);
    }
}