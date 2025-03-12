package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.dto.request.ScheduleCourseRequest;
import com.example.student_management_backend.dto.response.ScheduleResponse;
import com.example.student_management_backend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<ScheduleResponse>> getSchedule(
            @PathVariable int studentId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return ResponseEntity.ok(scheduleService.getSchedule(studentId, start, end));
    }

    @PostMapping("")
    public ResponseEntity<ScheduleResponse> createSchedule
            (@RequestBody ScheduleCourseRequest request) throws Exception {
        ScheduleResponse scheduleResponse = scheduleService.createSchedule(request);
        return new ResponseEntity<>(scheduleResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedule() {
        return ResponseEntity.ok(scheduleService.getAllSchedule());
    }

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


}
