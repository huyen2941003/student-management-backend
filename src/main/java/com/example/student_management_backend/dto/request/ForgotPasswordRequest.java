package com.example.student_management_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
}
