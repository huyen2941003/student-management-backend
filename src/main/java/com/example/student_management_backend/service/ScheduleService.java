package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.request.ScheduleCourseRequest;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CourseClassRepository courseClassRepository;

    public List<ScheduleResponse> getSchedule(int studentId, LocalDate startDate, LocalDate endDate) {
        return scheduleRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
    }

    public ScheduleResponse createSchedule(ScheduleCourseRequest request) throws Exception {
        Schedule schedule = new Schedule();
        CourseClass courseClass = courseClassRepository.
                findById(request.getCoursesId()).orElseThrow(
                () -> new Exception("CourseClass dont exists")
        );
        schedule.setCourses(courseClass);
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setDate(request.getDate());
        schedule.setRoom(request.getRoom());
        scheduleRepository.save(schedule);
        return new ScheduleResponse(schedule);
    }

    public List<ScheduleResponse> getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    public void deleteScheduleById(int id) throws Exception {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new Exception("Schedule dont exist with id:"+id)
        );
        scheduleRepository.delete(schedule);
    }
}