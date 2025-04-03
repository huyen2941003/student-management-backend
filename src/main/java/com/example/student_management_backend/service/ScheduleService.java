package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.ClassSchedule;
import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.DayOfWeek;
import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.request.ScheduleCourseRequest;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import com.example.student_management_backend.repository.ClassScheduleRepository;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.DayOfWeekRepository;
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
    private final DayOfWeekRepository dayOfWeekRepository;
    private final ClassScheduleRepository classScheduleRepository;
    public List<ScheduleResponse> getSchedule(int studentId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = scheduleRepository.getScheduleByStudentId(studentId, startDate, endDate);

        return results.stream().map(obj -> new ScheduleResponse(
                (String) obj[0],  // courseName
                ((java.sql.Date) obj[1]).toLocalDate(),  // Chuyển đổi date
                ((java.sql.Time) obj[2]).toLocalTime(),  // Chuyển đổi startTime
                ((java.sql.Time) obj[3]).toLocalTime(),  // Chuyển đổi endTime
                (String) obj[4],  // room
                (Integer) obj[5],  // lectureId
                obj[6] != null ? ((java.sql.Date) obj[6]).toLocalDate() : null,  // endDate, kiểm tra null
                obj[7] != null ? ((Number) obj[7]).longValue() : null,  // classScheduleId
                obj[8] != null ? ((Number) obj[8]).longValue() : null,  // dayOfWeekId
                (String) obj[9]   // dayName
        )).toList();
    }


    public ScheduleResponse createSchedule(ScheduleCourseRequest request) throws Exception {
        // Tìm CourseClass bằng ID
        CourseClass courseClass = courseClassRepository.findById(request.getCoursesId())
                .orElseThrow(() -> new Exception("CourseClass does not exist"));

        // Tạo đối tượng Schedule mới
        Schedule schedule = new Schedule();
        schedule.setCourses(courseClass);
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setRoom(request.getRoom());
        schedule.setDate(request.getDate());
        schedule.setEndDate(request.getEndDate());
        // Lưu Schedule (lưu các thông tin chung như thời gian bắt đầu, kết thúc, phòng học)
        schedule = scheduleRepository.save(schedule);

        // Thêm các ngày học cho môn học (ví dụ: Thứ 2, Thứ 4)
        for (DayOfWeek.DayName dayName : request.getDaysOfWeek()) {
            DayOfWeek dayOfWeek = dayOfWeekRepository.findByDayName(dayName)
                    .orElseThrow(() -> new Exception("Invalid day of the week"));

            // Tạo ClassSchedule để liên kết Schedule với các ngày trong tuần
            ClassSchedule classSchedule = new ClassSchedule();
            classSchedule.setSchedule(schedule);
            classSchedule.setDayOfWeek(dayOfWeek);

            classScheduleRepository.save(classSchedule);
        }

        // Trả về thông tin ScheduleResponse sau khi lưu thành công
        return new ScheduleResponse(
                schedule.getCourses().getCourses().getName(),
                schedule.getDate(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getRoom(),
                schedule.getCourses().getLecture().getId(),
                schedule.getEndDate()
        );
    }


    public List<ScheduleResponse> getAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponse(
                        schedule.getCourses().getCourses().getName(),
                        schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(),
                        schedule.getRoom(), schedule.getCourses().getLecture().getId(),schedule.getEndDate()))
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
                (Integer) obj[5],  // lectureId
                obj[6] != null ? ((java.sql.Date) obj[6]).toLocalDate() : null,  // endDate, kiểm tra null
                obj[7] != null ? ((Number) obj[7]).longValue() : null,  // classScheduleId
                obj[8] != null ? ((Number) obj[8]).longValue() : null,  // dayOfWeekId
                (String) obj[9]   // dayName
        )).toList();
    }
}