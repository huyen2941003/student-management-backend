package com.example.student_management_backend.domain;

import java.time.LocalDateTime;

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
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    @JsonIgnore
    private Role role;

    private String fcmToken;

    private String resetToken;
    private LocalDateTime resetTokenExpiry;

}
