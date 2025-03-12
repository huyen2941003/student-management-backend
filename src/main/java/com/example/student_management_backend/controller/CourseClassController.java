package com.example.student_management_backend.controller;

import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import com.example.student_management_backend.service.ICourseClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/courseClass")
@RequiredArgsConstructor
public class CourseClassController {
    private final ICourseClassService courseClassService;
    @GetMapping("/{userId}")
    private List<CourseClassScheduleResponse> getCourseClass(@PathVariable int userId)
    {
        //Long userID = authUtil.loggedInUserId();
        return courseClassService.getCourseClassSchedule(userId);
    }
}
