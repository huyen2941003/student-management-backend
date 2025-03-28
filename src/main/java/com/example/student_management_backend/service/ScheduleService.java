package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.request.ScheduleCourseRequest;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CourseClassRepository courseClassRepository;

    public List<ScheduleResponse> getSchedule(int studentId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = scheduleRepository.getScheduleByStudentId(studentId, startDate, endDate);

        return results.stream().map(obj -> new ScheduleResponse(
                (String) obj[0],  // courseName
                ((java.sql.Date) obj[1]).toLocalDate(),  // Chuyển đổi date
                ((java.sql.Time) obj[2]).toLocalTime(),  // Chuyển đổi startTime
                ((java.sql.Time) obj[3]).toLocalTime(),  // Chuyển đổi endTime
                (String) obj[4],  // room
                (Integer) obj[5]   // lectureId
        )).toList();
    }

    public ScheduleResponse createSchedule(ScheduleCourseRequest request) throws Exception {
        CourseClass courseClass = courseClassRepository.findById(request.getCoursesId())
                .orElseThrow(() -> new Exception("CourseClass does not exist"));

        Schedule schedule = new Schedule();
        schedule.setCourses(courseClass);
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setDate(request.getDate());
        schedule.setRoom(request.getRoom());

        schedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(schedule.getCourses().getCourses().getName(),
                schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getRoom(),
                schedule.getCourses().getLecture().getId());
    }

    public List<ScheduleResponse> getAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponse(
                        schedule.getCourses().getCourses().getName(),
                        schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(),
                        schedule.getRoom(), schedule.getCourses().getLecture().getId()))
                .collect(Collectors.toList());
    }

    public void deleteScheduleById(int id) throws Exception {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new Exception("Schedule does not exist with id: " + id));
        scheduleRepository.delete(schedule);
    }

    public List<Schedule> filterSchedules(LocalDate date, LocalTime startTime, LocalTime endTime, String room) {
        Specification<Schedule> spec = Specification.where(null);

        if (date != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date));
        if (startTime != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"), startTime));
        if (endTime != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endTime"), endTime));
        if (room != null && !room.isEmpty()) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("room"), "%" + room + "%"));

        return scheduleRepository.findAll(spec);
    }

    public Page<Schedule> searchSchedules(
            LocalDate date, LocalDate dateFrom, LocalDate dateTo, LocalTime startTime,
            LocalTime endTime, String room, String courseName, Integer classId, Pageable pageable) {

        Specification<Schedule> spec = Specification.where(null);

        if (date != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date));
        if (dateFrom != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), dateFrom));
        if (dateTo != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), dateTo));
        if (startTime != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"), startTime));
        if (endTime != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endTime"), endTime));
        if (room != null && !room.isEmpty()) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("room"), "%" + room + "%"));
        if (courseName != null && !courseName.isEmpty()) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("courses").get("courses").get("name"), "%" + courseName + "%"));
        if (classId != null) spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courses").get("id"), classId));

        return scheduleRepository.findAll(spec, pageable);
    }


    public List<ScheduleResponse> getScheduleByLectureId(Integer lectureId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = scheduleRepository.getScheduleByLectureId(lectureId, startDate, endDate);

        return results.stream().map(obj -> new ScheduleResponse(
                (String) obj[0],  // courseName
                ((java.sql.Date) obj[1]).toLocalDate(),  // Chuyển đổi date
                ((java.sql.Time) obj[2]).toLocalTime(),  // Chuyển đổi startTime
                ((java.sql.Time) obj[3]).toLocalTime(),  // Chuyển đổi endTime
                (String) obj[4],  // room
                (Integer) obj[5]   // lectureId
        )).toList();
    }
}