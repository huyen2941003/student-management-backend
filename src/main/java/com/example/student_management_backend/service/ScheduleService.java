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
import java.util.*;
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
                (Integer) obj[0],
                (String) obj[1], // courseName
                ((java.sql.Date) obj[2]).toLocalDate(), // Chuyển đổi date
                ((java.sql.Time) obj[3]).toLocalTime(), // Chuyển đổi startTime
                ((java.sql.Time) obj[4]).toLocalTime(), // Chuyển đổi endTime
                (String) obj[5], // room
                (Integer) obj[6], // lectureId
                (String) obj[7], // fullname
                obj[8] != null ? ((java.sql.Date) obj[8]).toLocalDate() : null, // endDate, kiểm tra null
                obj[9] != null ? ((Number) obj[9]).longValue() : null, // classScheduleId
                obj[10] != null ? ((Number) obj[10]).longValue() : null, // dayOfWeekId
                (String) obj[11])).toList();
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
        // Lưu Schedule (lưu các thông tin chung như thời gian bắt đầu, kết thúc, phòng
        // học)
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
                schedule.getEndDate());
    }

    public List<ScheduleResponse> getAllSchedule() {
        List<Object[]> results = scheduleRepository.getAllSchedules();

        // Sử dụng Map để nhóm dữ liệu theo scheduleId
        Map<Long, ScheduleResponse> scheduleMap = new LinkedHashMap<>();

        for (Object[] obj : results) {
            Long id = ((Number) obj[0]).longValue();
            String courseName = (String) obj[1];
            LocalDate date = ((java.sql.Date) obj[2]).toLocalDate();
            LocalTime startTime = ((java.sql.Time) obj[3]).toLocalTime();
            LocalTime endTime = ((java.sql.Time) obj[4]).toLocalTime();
            String room = (String) obj[5];
            Integer lectureId = (Integer) obj[6];
            String fullName = (String) obj[7];
            LocalDate endDate = obj[8] != null ? ((java.sql.Date) obj[8]).toLocalDate() : null;
            Long classScheduleId = obj[9] != null ? ((Number) obj[9]).longValue() : null; // classScheduleId
            Long dayOfWeekId = obj[10] != null ? ((Number) obj[10]).longValue() : null;
            String dayName = obj[11] != null ? (String) obj[11] : null;
            Integer courseClassId = (Integer) obj[12];

            // Nếu Schedule đã tồn tại trong Map, chỉ thêm dayName
            if (scheduleMap.containsKey(id)) {
                scheduleMap.get(id).getDayNames().add(dayName);
            } else {
                ScheduleResponse scheduleResponse = new ScheduleResponse(
                        Math.toIntExact(id), courseName, date, startTime, endTime, room, lectureId, fullName, endDate,
                        classScheduleId, dayOfWeekId, new ArrayList<>(), courseClassId);
                if (dayName != null) {
                    scheduleResponse.getDayNames().add(dayName);
                }
                scheduleMap.put(id, scheduleResponse);
            }
        }
        return new ArrayList<>(scheduleMap.values());
    }

    public void deleteScheduleById(int id) throws Exception {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new Exception("Schedule does not exist with id: " + id));
        scheduleRepository.delete(schedule);
    }

    public List<Schedule> filterSchedules(LocalDate date, LocalTime startTime, LocalTime endTime, String room) {
        Specification<Schedule> spec = Specification.where(null);

        if (date != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date));
        if (startTime != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"), startTime));
        if (endTime != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endTime"), endTime));
        if (room != null && !room.isEmpty())
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("room"), "%" + room + "%"));

        return scheduleRepository.findAll(spec);
    }

    public Page<Schedule> searchSchedules(
            LocalDate date, LocalDate dateFrom, LocalDate dateTo, LocalTime startTime,
            LocalTime endTime, String room, String courseName, Integer classId, Pageable pageable) {

        Specification<Schedule> spec = Specification.where(null);

        if (date != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date));
        if (dateFrom != null)
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), dateFrom));
        if (dateTo != null)
            spec = spec
                    .and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), dateTo));
        if (startTime != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"), startTime));
        if (endTime != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endTime"), endTime));
        if (room != null && !room.isEmpty())
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("room"), "%" + room + "%"));
        if (courseName != null && !courseName.isEmpty())
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder
                    .like(root.get("courses").get("courses").get("name"), "%" + courseName + "%"));
        if (classId != null)
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courses").get("id"), classId));

        return scheduleRepository.findAll(spec, pageable);
    }

    public List<ScheduleResponse> getScheduleByLectureId(Integer lectureId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = scheduleRepository.getScheduleByLectureId(lectureId, startDate, endDate);

        return results.stream().map(obj -> new ScheduleResponse(
                (Integer) obj[0],
                (String) obj[1], // courseName
                ((java.sql.Date) obj[2]).toLocalDate(), // Chuyển đổi date
                ((java.sql.Time) obj[3]).toLocalTime(), // Chuyển đổi startTime
                ((java.sql.Time) obj[4]).toLocalTime(), // Chuyển đổi endTime
                (String) obj[5], // room
                (Integer) obj[6], // lectureId
                (String) obj[7], // fullname
                obj[8] != null ? ((java.sql.Date) obj[8]).toLocalDate() : null, // endDate, kiểm tra null
                obj[9] != null ? ((Number) obj[9]).longValue() : null, // classScheduleId
                obj[10] != null ? ((Number) obj[10]).longValue() : null, // dayOfWeekId
                (String) obj[11])).toList();
    }


}