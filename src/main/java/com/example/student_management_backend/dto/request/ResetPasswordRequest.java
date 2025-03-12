package com.example.student_management_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @NotBlank(message = "Token không thể trống")
    private String token;

    @NotBlank(message = "New password không thể trống")
    private String newPassword;
}