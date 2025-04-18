package com.example.student_management_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class EnrollmentsRequest {
    private int classId;
    private String status;
}
