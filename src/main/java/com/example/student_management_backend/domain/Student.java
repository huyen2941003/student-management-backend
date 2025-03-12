package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.example.student_management_backend.util.constant.GenderEnum;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "student_code", nullable = false, length = 50, unique = true)
    private String studentCode;

    @Column(name = "email", nullable = true, length = 50)
    private String email;

    @Column(name = "fullName", nullable = false, length = 50)
    private String fullName;

    @Column(name = "dob")
    private LocalDateTime dob;

    @Column(name = "gender", nullable = false, length = 10)
    private GenderEnum gender;

    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    @Column(name = "address", nullable = true, length = 255)
    private String address;

    @Column(name = "major", nullable = false, length = 100)
    private String major;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "avatar", nullable = true)
    private String avatar;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrolment> enrolments;

}