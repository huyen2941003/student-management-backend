package com.example.student_management_backend.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String keyword; // Từ khóa tìm kiếm
    private String type; // Loại tìm kiếm: course, schedule, exam, grade, notification, account
    private Integer studentId; // ID sinh viên (dùng cho bảng điểm)
    private LocalDate date; // Ngày (dùng cho lịch học, lịch thi)
}