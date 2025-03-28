package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Enrollments;
import com.example.student_management_backend.dto.request.EnrollmentsRequest;
import com.example.student_management_backend.dto.response.StudentGradeResponse;
import com.example.student_management_backend.service.EnrollmentService;
import com.example.student_management_backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final AuthUtil authUtil;

    @PostMapping()
    public ResponseEntity<Enrollments> registerCourseClass(@RequestBody EnrollmentsRequest request) throws Exception {
        Integer studentId = authUtil.loggedInStudentId();

        Enrollments enrollments = enrollmentService.registerCourseClass(request,studentId);
        if (enrollments == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(enrollments, HttpStatus.CREATED);
    }
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<StudentGradeResponse>> getByClassId(@PathVariable int classId) {
        List<StudentGradeResponse> response = enrollmentService.getStudentGradesByClassId(classId);
        return ResponseEntity.ok(response);
    }

}
