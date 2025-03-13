package com.example.student_management_backend.dto.response.department;

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
public class DepartmentsResponse {
    private Integer id;
    private String departmentName;
    private String description;
}