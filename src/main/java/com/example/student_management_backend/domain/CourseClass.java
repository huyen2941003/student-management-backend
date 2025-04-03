package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
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
    @JsonIgnore
    Courses courses;

    int semester;
    @ManyToOne
    @JoinColumn(name = "lectureId", referencedColumnName = "id")
    @JsonIgnore
    Lectures lecture;
    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

}
