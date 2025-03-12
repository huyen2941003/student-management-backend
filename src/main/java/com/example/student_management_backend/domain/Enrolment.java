package com.example.student_management_backend.domain;

import java.time.Instant;

import com.example.student_management_backend.util.constant.GenderEnum;
import com.example.student_management_backend.util.constant.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enrolments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrolment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, length = 10)
    private StatusEnum status;

    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
