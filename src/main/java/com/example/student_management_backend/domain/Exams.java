package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Exams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "exam_date", nullable = false)
    @Temporal(TemporalType.DATE) // Chỉ định rõ kiểu dữ liệu
    LocalDate examDate;

    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "classId", referencedColumnName = "id")
    CourseClass classes;

    String room;
}
