package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data

@RequiredArgsConstructor
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100, nullable = false, unique = true)
    String name;

    int credit;

    int semester;

    @ManyToOne
    @JoinColumn(name = "majorId", referencedColumnName = "id")
    Majors majors;

}
