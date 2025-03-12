package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseService {
    public final CourseRepository courseRepository;


    public List<Courses> getCourseByUserId(int userId) {
        return courseRepository.getCourses(userId);
    }
}
