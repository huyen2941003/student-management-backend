package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "created_date", nullable = false)
    LocalDateTime createdDate;

    @Column(name = "max_student", nullable = false)
    int maxStudent;
    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    Courses courses;
    int semester;

}
