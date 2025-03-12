package com.example.student_management_backend.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_code", nullable = false, length = 50, unique = true)
    private String subjectCode;

    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "description", length = 255)
    private String description;

    private Instant createdAt;
    private Instant updatedAt;
}