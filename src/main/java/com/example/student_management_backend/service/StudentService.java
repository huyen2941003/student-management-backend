package com.example.student_management_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Lấy thông tin sinh viên theo ID
    public StudentResponse getStudentById(Integer id) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));
        return new StudentResponse(student);
    }

    public List<StudentResponse> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public Students updateStudent(Integer id, Students updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFullName(updatedStudent.getFullName());
                    student.setDob(updatedStudent.getDob());
                    student.setEmail(updatedStudent.getEmail());
                    student.setPhone(updatedStudent.getPhone());
                    student.setAddress(updatedStudent.getAddress());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    public Students updateStudentByAdmin(Integer id, Students updateStudentByAdmin) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFullName(updateStudentByAdmin.getFullName());
                    student.setDob(updateStudentByAdmin.getDob());
                    student.setGender(updateStudentByAdmin.getGender()); // Kiểm tra giá trị
                    student.setEmail(updateStudentByAdmin.getEmail());
                    student.setPhone(updateStudentByAdmin.getPhone());
                    student.setAddress(updateStudentByAdmin.getAddress());
                    student.setStatus(updateStudentByAdmin.getStatus()); // Kiểm tra giá trị
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }
}