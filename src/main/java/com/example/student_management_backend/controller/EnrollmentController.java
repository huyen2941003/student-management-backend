package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Enrollments;
import com.example.student_management_backend.dto.request.EnrollmentsRequest;
import com.example.student_management_backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping()
    public ResponseEntity<Enrollments> registerCourseClass(@RequestBody EnrollmentsRequest request) throws Exception {
        Enrollments enrollments = enrollmentService.registerCourseClass(request);
        if (enrollments == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(enrollments, HttpStatus.CREATED);
    }

}
