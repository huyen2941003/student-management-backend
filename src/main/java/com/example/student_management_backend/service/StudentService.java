package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Override
    public Students getStudentById(int id) {
        Students students = null;
        try {
            students = studentRepository.findById(id).orElseThrow(
                        () -> new Exception("Student not existed"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return students;
    }

}
