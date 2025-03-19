package com.example.student_management_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "midterm_score")
    private double midtermScore;

    @Column(name = "final_score")
    private double finalScore;

    @Column(name = "total_score")
    private double totalScore;

    private String grade;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    @JsonIgnore
    Students students;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    @JsonIgnore
    Courses courses;

    String semester;
}
