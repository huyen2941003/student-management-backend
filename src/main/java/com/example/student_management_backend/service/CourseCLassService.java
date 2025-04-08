package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.dto.request.CourseClassRequest;
import com.example.student_management_backend.dto.response.CourseClassResponse;
import com.example.student_management_backend.dto.response.CourseClassScheduleResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseCLassService implements ICourseClassService {
    private final CourseClassRepository courseClassRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<CourseClassScheduleResponse> getCourseClassSchedule(int studentId) {
        return courseClassRepository.getCourseChedule(studentId);
    }

    @Override
    public List<CourseClassResponse> getCourseClassByTeacherId(Lectures lectureId) {
        return courseClassRepository.findByLectureId(lectureId.getId());
    }

    @Override
    public CourseClassResponse createCourseClass(Lectures lectureId, CourseClassRequest request) throws Exception {
        CourseClass courseClass = new CourseClass();
        Courses courses = courseRepository.findById(request.getCourseId()).orElseThrow(
                () -> new Exception("Courses k tồn tại"));
        courseClass.setCourses(courses);
        courseClass.setMaxStudent(request.getMaxStudent());
        courseClass.setCreatedDate(LocalDateTime.now());
        courseClass.setLecture(lectureId);
        courseClass.setSemester(courses.getSemester());
        courseClassRepository.save(courseClass);

        return new CourseClassResponse(courseClass.getId(), courseClass.getCourses().getId(), courseClass.getSemester(),
                courseClass.getMaxStudent(),
                courseClass.getCourses().getName());
    }

}
