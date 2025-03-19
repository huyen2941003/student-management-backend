package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.dto.request.CourseRequest;
import com.example.student_management_backend.dto.response.CourseResponse;
import com.example.student_management_backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public List<CourseResponse> getCourseByUserId(@PathVariable int userId) {
        return courseService.getCourseByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Courses> createCourse(@RequestBody CourseRequest request) throws Exception {
        Courses courses = courseService.createCourse(request);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable int id) throws Exception {
        courseService.deleteCourseById(id);
        return ResponseEntity.ok().body("Course deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // @GetMapping("/search")
    // public ResponseEntity<List<CourseResponse>> searchCourses(
    // @RequestParam(required = false) String keyword,
    // @RequestParam(required = false) String semester,
    // @RequestParam(required = false) Double minGrade,
    // @RequestParam(required = false) Double maxGrade) {
    // List<CourseResponse> courses = courseService.searchCourses(keyword,
    // semester, minGrade, maxGrade);
    // return ResponseEntity.ok(courses);
    // }

    // Nhập tên, số tín chỉ, học kì để tìm kiếm được môn học.
    @GetMapping("/search")
    public ResponseEntity<List<Courses>> searchCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer credit,
            @RequestParam(required = false) Integer semester) {
        List<Courses> courses = courseService.searchCourses(name, credit, semester);
        return ResponseEntity.ok(courses);
    }

}
