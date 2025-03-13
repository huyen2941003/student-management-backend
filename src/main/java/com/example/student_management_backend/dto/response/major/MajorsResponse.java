package com.example.student_management_backend.dto.response.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MajorsResponse {
    private Integer id;
    private String majorName;
    private Integer departmentId;
    private String departmentName;

    public MajorsResponse(Integer id, String majorName, Integer departmentId) {
        this.id = id;
        this.majorName = majorName;
        this.departmentId = departmentId;
    }
}