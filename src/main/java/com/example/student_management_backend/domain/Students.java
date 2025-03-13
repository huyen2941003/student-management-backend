package com.example.student_management_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.example.student_management_backend.util.constant.GenderEnum;
import com.example.student_management_backend.util.constant.StatusEnum;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Students extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "full_name")
    private String fullName;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String email;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "majorId", referencedColumnName = "id")
    Majors majors;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "id")
    Departments departments;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

}
