package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.dto.response.CourseClassResponse;
import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import com.example.student_management_backend.service.ICourseClassService;
import com.example.student_management_backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/courseClass")
@RequiredArgsConstructor
public class CourseClassController {
    private final ICourseClassService courseClassService;
    private final AuthUtil authUtil;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    private List<CourseClassScheduleResponse> getCourseClass() throws Exception {
        Long userID = authUtil.loggedInUserId();
        return courseClassService.getCourseClassSchedule(Math.toIntExact(userID));
    }

    @GetMapping("/userId")
    private List<CourseClassResponse> getCourseClassByTeacherId() throws Exception {
        Lectures lectureId = authUtil.loggedInLectureId();
        return courseClassService.getCourseClassByTeacherId(lectureId);
    }
}
