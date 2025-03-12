package com.example.student_management_backend.domain;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "majors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "major_name", nullable = false)
    private String major_name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Class> classes;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrolment> enrolments;

}