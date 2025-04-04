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
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseCLassService implements ICourseClassService {
    private final CourseClassRepository courseClassRepository;
    private final CourseRepository courseRepository;

        @Override
        public List<CourseClassScheduleResponse> getCourseClassSchedule(int studentId) {
            List<Object[]> results = courseClassRepository.getCourseSchedule(studentId);

            return results.stream().map(result -> {
                Integer courseId = (Integer) result[0];
                String courseName = (String) result[1];
                Integer courseClassId = (Integer) result[2];
                Integer semester = (Integer) result[3];
                Integer maxStudent = (Integer) result[4];
                Integer scheduleId = (Integer) result[5];
                LocalTime startTime = (result[6] != null) ? ((java.sql.Time) result[6]).toLocalTime() : null;
                LocalTime endTime = (result[7] != null) ? ((java.sql.Time) result[7]).toLocalTime() : null;
                LocalDate startDate = (result[8] != null) ? ((java.sql.Date) result[8]).toLocalDate() : null;
                LocalDate endDate = (result[9] != null) ? ((java.sql.Date) result[9]).toLocalDate() : null;
                String room = (String) result[10];
                String lectureFullname = (String) result[11]; // Lấy full_name của giảng viên
                String classScheduleIds = (String) result[12];
                String classDays = (String) result[13];

                return new CourseClassScheduleResponse(
                        courseClassId,
                        courseId,
                        courseName,
                        semester,
                        lectureFullname, // Sử dụng lectureFullname
                        null, // lectureId - không có trong query
                        scheduleId,
                        startTime,
                        endTime,
                        startDate,
                        endDate,
                        room,
                        classScheduleIds,
                        classDays
                );
            }).collect(Collectors.toList());
        }



    @Override
    public List<CourseClassResponse> getCourseClassByTeacherId(Lectures lectureId) {
        return courseClassRepository.findByLectureId(lectureId.getId());
    }

    @Override
    public CourseClassResponse createCourseClass(Lectures lectureId, CourseClassRequest request) throws Exception {
        CourseClass courseClass = new CourseClass();
        Courses courses = courseRepository.findById(request.getCourseId()).orElseThrow(
                () -> new Exception("Courses k tồn tại")
        );
        courseClass.setCourses(courses);
        courseClass.setMaxStudent(request.getMaxStudent());
        courseClass.setCreatedDate(LocalDateTime.now());
        courseClass.setLecture(lectureId);
        courseClass.setSemester(courses.getSemester());
        courseClassRepository.save(courseClass);

        return new CourseClassResponse(courseClass.getId(),courseClass.getCourses().getId()
                ,courseClass.getSemester(),courseClass.getMaxStudent(),
                courseClass.getCourses().getName());
    }


}
