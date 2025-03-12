package com.example.student_management_backend.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GpaResponse {
    int semester;
    double gpa;
}
