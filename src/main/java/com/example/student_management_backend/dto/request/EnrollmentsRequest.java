package com.example.student_management_backend.dto.request;

import com.example.student_management_backend.domain.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class EnrollmentsRequest {
    private int classId;
    private int studentId;
    private String status;
}
