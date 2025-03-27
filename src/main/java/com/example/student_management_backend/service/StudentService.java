package com.example.student_management_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileStorageService fileStorageService;

    StudentService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @Transactional
    public Students updateStudent(Integer id, Students updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    // Cập nhật thông tin cơ bản
                    student.setFullName(updatedStudent.getFullName());
                    student.setDob(updatedStudent.getDob());
                    student.setEmail(updatedStudent.getEmail());
                    student.setPhone(updatedStudent.getPhone());
                    student.setAddress(updatedStudent.getAddress());

                    student.setGender(student.getGender());
                    student.setStatus(student.getStatus());
                    // Cập nhật avatar nếu có
                    if (updatedStudent.getAvatar() != null) {
                        student.setAvatar(updatedStudent.getAvatar());
                    }

                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    @Transactional
    public Students updateStudentByAdmin(Integer id, Students updateStudentByAdmin) {
        return studentRepository.findById(id)
                .map(student -> {
                    // Cập nhật thông tin cơ bản
                    student.setFullName(updateStudentByAdmin.getFullName());
                    student.setDob(updateStudentByAdmin.getDob());
                    student.setEmail(updateStudentByAdmin.getEmail());
                    student.setPhone(updateStudentByAdmin.getPhone());
                    student.setAddress(updateStudentByAdmin.getAddress());

                    student.setGender(updateStudentByAdmin.getGender());
                    student.setStatus(updateStudentByAdmin.getStatus());
                    student.setMajors(updateStudentByAdmin.getMajors());
                    student.setDepartments(updateStudentByAdmin.getDepartments());

                    // Cập nhật avatar nếu có
                    if (updateStudentByAdmin.getAvatar() != null) {
                        student.setAvatar(updateStudentByAdmin.getAvatar());
                    }

                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    public Integer getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(Math.toIntExact(userId)).getId();

    }

    @Transactional
    public void deleteStudent(Integer studentId) {
        studentRepository.findUserIdByStudentId(studentId).ifPresent(userId -> {
            userRepository.hardDeleteUser(userId);
        });

        studentRepository.hardDelete(studentId);
    }
}