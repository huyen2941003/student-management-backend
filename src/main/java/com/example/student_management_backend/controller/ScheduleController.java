package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.request.ScheduleCourseRequest;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import com.example.student_management_backend.service.ScheduleService;
import com.example.student_management_backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final AuthUtil authUtil;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/studentId")
    public ResponseEntity<List<ScheduleResponse>> getSchedule(
            @RequestParam String startDate,
            @RequestParam String endDate) throws Exception {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Integer studentId = authUtil.loggedInStudentId();
        return ResponseEntity.ok(scheduleService.getSchedule(studentId, start, end));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleCourseRequest request)
            throws Exception {
        ScheduleResponse scheduleResponse = scheduleService.createSchedule(request);
        return new ResponseEntity<>(scheduleResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getAllSchedule() {
        return ResponseEntity.ok(scheduleService.getAllSchedule());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable int id) {
        try {
            scheduleService.deleteScheduleById(id);
            return ResponseEntity.ok().body("Schedule deleted successfully");
        } catch (Exception e) {
            if (e.getMessage().contains("Schedule not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the schedule");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<Page<Schedule>> searchSchedules(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) LocalTime startTime,
            @RequestParam(required = false) LocalTime endTime,
            @RequestParam(required = false) String room,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) Integer classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));

        Page<Schedule> schedules = scheduleService.searchSchedules(
                date, dateFrom, dateTo,
                startTime, endTime,
                room, courseName, classId,
                pageable);

        return ResponseEntity.ok(schedules);
    }
}
