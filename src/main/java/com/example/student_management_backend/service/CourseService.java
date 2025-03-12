package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Announcements;
import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.dto.request.CourseRequest;
import com.example.student_management_backend.dto.response.AnnouncementResponse;
import com.example.student_management_backend.dto.response.CourseResponse;
import com.example.student_management_backend.repository.CourseRepository;
import com.example.student_management_backend.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseService {
    public final CourseRepository courseRepository;
    public final MajorRepository majorRepository;


    public List<CourseResponse> getCourseByUserId(int userId) {
        List<Courses> courses = courseRepository.getCourses(userId);

        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Courses createCourse(CourseRequest request) throws Exception {
        Courses courses = new Courses();
        Majors majors =majorRepository.findById(request.getMajorId()).orElseThrow(
                ()-> new Exception("Major dont exist")
        );
        courses.setCredit(request.getCredit());
        courses.setName(request.getName());
        courses.setSemester(request.getSemester());
        courses.setMajors(majors);
        return courseRepository.save(courses);
    }

    public void deleteCourseById(int id) throws Exception {
        Courses courses = courseRepository.findById(id).orElseThrow(
                ()-> new Exception("Course dont exist")
        );
        courseRepository.deleteById(id);
    }

    public List<CourseResponse> getAllCourses() {
        List<Courses> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }
    private CourseResponse mapToResponse(Courses courseResponse) {
        return new CourseResponse(
                courseResponse.getId(),
                courseResponse.getName(),
                courseResponse.getCredit(),
                courseResponse.getSemester(),
                courseResponse.getMajors().getMajorName()
        );
    }
    public List<CourseResponse> searchCourses(String keyword, String semester, Double minGrade, Double maxGrade) {
        List<Courses>  courses = courseRepository.searchCourses(keyword, semester, minGrade, maxGrade);
        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }
}
