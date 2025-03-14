package com.example.student_management_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AnnouncementsRequest {
    int userId;
    String title;
    String content;
}
