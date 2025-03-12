package com.example.student_management_backend.dto.response;

import com.example.student_management_backend.domain.User;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AnnouncementResponse {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
}
