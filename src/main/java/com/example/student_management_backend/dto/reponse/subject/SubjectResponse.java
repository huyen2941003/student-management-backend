package com.example.student_management_backend.dto.reponse.subject;

import com.example.student_management_backend.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private Integer id;
    private String subjectCode;
    private String subjectName;
    private Integer credits;
    private String description;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.subjectCode = subject.getSubjectCode();
        this.subjectName = subject.getSubjectName();
        this.credits = subject.getCredits();
        this.description = subject.getDescription();
    }
}