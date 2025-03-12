package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

import com.example.student_management_backend.util.constant.GenderEnum;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lecturer_code", nullable = false, length = 50, unique = true)
    private String lecturerCode;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "dob")
    private LocalDateTime dob;

    @Column(name = "gender", nullable = false, length = 10)
    private GenderEnum gender;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

}