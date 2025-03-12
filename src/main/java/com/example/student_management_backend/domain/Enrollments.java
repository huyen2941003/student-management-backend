package com.example.student_management_backend.domain;

import java.io.ObjectInputFilter.Status;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Enrollments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "classId", referencedColumnName = "id")
    CourseClass classes;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    Students students;
}
