package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
public class Majors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name="major_name",nullable = false,unique = true)
    String majorName;
    @ManyToOne
    @JoinColumn(name = "departmentId",referencedColumnName = "id")
    Departments departments;
}
