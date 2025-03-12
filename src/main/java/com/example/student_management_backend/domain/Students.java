package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="student")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Students extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name="full_name")
    private String fullName;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "majorId",referencedColumnName = "id")
    Majors majors;
    @ManyToOne
    @JoinColumn(name = "departmentId",referencedColumnName = "id")
    Departments departments;
}
