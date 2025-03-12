package com.example.student_management_backend.service;

import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;

import java.util.List;

public interface ICourseClassService {
    List<CourseClassScheduleResponse> getCourseClassSchedule(int userId);
}
