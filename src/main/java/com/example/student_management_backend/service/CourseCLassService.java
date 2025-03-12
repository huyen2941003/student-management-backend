package com.example.student_management_backend.service;

import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseCLassService implements ICourseClassService {
    private final CourseClassRepository courseClassRepository;
    @Override
    public List<CourseClassScheduleResponse> getCourseClassSchedule(int userId) {
        return courseClassRepository.getCourseChedule(userId) ;
    }
}
