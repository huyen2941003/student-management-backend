package com.example.student_management_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.domain.Student;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.reponse.ResStudentDTO;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Student handleCreateStudent(Student student) {
        // Kiểm tra và thiết lập thông tin người dùng (user) nếu có
        if (student.getUser() != null) {
            Optional<User> userOptional = this.userRepository.findById(student.getUser().getId());
            student.setUser(userOptional.isPresent() ? userOptional.get() : null);
        }

        return this.studentRepository.save(student);
    }

    public void handleDeleteStudent(Integer id) {
        this.studentRepository.deleteById(id);
    }

    public Student fetchStudentById(Integer id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    public Student handleUpdateStudent(Student reqStudent) {
        Student currentStudent = this.fetchStudentById(reqStudent.getId());
        if (currentStudent != null) {
            currentStudent.setFullName(reqStudent.getFullName());
            currentStudent.setDob(reqStudent.getDob());
            currentStudent.setGender(reqStudent.getGender());
            currentStudent.setPhone(reqStudent.getPhone());
            currentStudent.setAddress(reqStudent.getAddress());
            currentStudent.setMajor(reqStudent.getMajor());
            currentStudent.setYear(reqStudent.getYear());

            // Kiểm tra và cập nhật thông tin người dùng (user) nếu có
            if (reqStudent.getUser() != null) {
                Optional<User> userOptional = this.userRepository.findById(reqStudent.getUser().getId());
                currentStudent.setUser(userOptional.isPresent() ? userOptional.get() : null);
            }

            // Cập nhật
            currentStudent = this.studentRepository.save(currentStudent);
        }
        return currentStudent;
    }

    public ResStudentDTO convertToResStudentDTO(Student student) {
        ResStudentDTO res = new ResStudentDTO();
        res.setId(student.getId());
        res.setEmail(student.getEmail());
        res.setFullName(student.getFullName());
        res.setDob(student.getDob());
        res.setGender(student.getGender());
        res.setPhone(student.getPhone());
        res.setAddress(student.getAddress());
        res.setMajor(student.getMajor());
        res.setYear(student.getYear());
        res.setCreatedAt(student.getCreatedAt());
        res.setUpdatedAt(student.getUpdatedAt());
        return res;
    }

}