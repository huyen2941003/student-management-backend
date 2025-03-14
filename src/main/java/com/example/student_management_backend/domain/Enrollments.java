package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollments extends BaseEntity {
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
