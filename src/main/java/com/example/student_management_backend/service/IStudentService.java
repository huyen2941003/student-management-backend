package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Students;

public interface IStudentService {
    Students getStudentById(int id) throws Exception;
}
