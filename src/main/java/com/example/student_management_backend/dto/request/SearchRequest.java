package com.example.student_management_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String subjectName;
    private String subjectCode;
    private Integer studentId;
}