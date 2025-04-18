package com.example.student_management_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Majors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "major_name", nullable = false, unique = true)
    String majorName;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "id")
    @JsonIgnore
    Departments departments;

    // @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private List<EnrollmentsClass> enrollmentsClasses;
}
