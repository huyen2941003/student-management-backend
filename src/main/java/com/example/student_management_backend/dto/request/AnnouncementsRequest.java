package com.example.student_management_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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