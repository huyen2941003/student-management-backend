package com.example.student_management_backend.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Student;
import com.example.student_management_backend.dto.StudentDTO;
import com.example.student_management_backend.dto.reponse.student.StudentResponse;
import com.example.student_management_backend.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Lấy thông tin sinh viên theo ID
    public StudentResponse getStudentById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));
        return new StudentResponse(student);
    }

    // Cập nhật thông tin sinh viên
    public StudentResponse updateStudent(Integer id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));

        // Cập nhật thông tin từ DTO
        student.setStudentCode(studentDTO.getStudentCode());
        student.setEmail(studentDTO.getEmail());
        student.setFullName(studentDTO.getFullName());
        student.setDob(studentDTO.getDob());
        student.setGender(studentDTO.getGender());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());
        student.setMajor(studentDTO.getMajor());
        student.setYear(studentDTO.getYear());
        student.setUpdatedAt(Instant.now());

        // Lưu thông tin cập nhật vào database
        studentRepository.save(student);

        return new StudentResponse(student);
    }

}