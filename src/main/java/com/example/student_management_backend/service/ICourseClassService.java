package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.dto.request.CourseClassRequest;
import com.example.student_management_backend.dto.response.CourseClassResponse;
import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;

import java.util.List;

public interface ICourseClassService {
    List<CourseClassScheduleResponse> getCourseClassSchedule(int userId);


    List<CourseClassResponse> getCourseClassByTeacherId(Lectures lectureId);

    CourseClassResponse createCourseClass(Lectures lectureId, CourseClassRequest request) throws Exception;
}
