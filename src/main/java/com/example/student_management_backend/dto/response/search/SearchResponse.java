package com.example.student_management_backend.dto.response.search;

import java.util.List;
import com.example.student_management_backend.domain.Announcements;
import com.example.student_management_backend.domain.Courses;
import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.domain.Grades;
import com.example.student_management_backend.domain.Schedule;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private List<Courses> courses; // Kết quả môn học
    private List<Schedule> schedules; // Kết quả lịch học
    private List<Exams> exams; // Kết quả lịch thi
    private List<Grades> grades; // Kết quả bảng điểm
    private List<Announcements> announcements; // Kết quả thông báo
    private List<User> user; // Kết quả tài khoản
    private List<Students> students; // Kết quả sinh viên
}