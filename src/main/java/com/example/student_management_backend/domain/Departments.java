package com.example.student_management_backend.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@RequiredArgsConstructor
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "department_name", nullable = false, unique = true)
    String departmentName;
    String description;

    @OneToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Majors> majors;
}
