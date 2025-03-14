package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.CourseClass;
import com.example.student_management_backend.domain.Enrollments;
import com.example.student_management_backend.domain.Status;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.request.EnrollmentsRequest;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.EnrollmentsRepository;
import com.example.student_management_backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private  final EnrollmentsRepository enrollmentsRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    public Enrollments registerCourseClass(EnrollmentsRequest request) throws Exception {
        Students students = studentRepository.findById(request.getStudentId())
                .orElseThrow(
                        ()-> new Exception("Student dont exist")
                );
        CourseClass courseClass = courseClassRepository.findById(request.getClassId())
                .orElseThrow(
                        ()-> new Exception("CourseClass dont exist")
                );

        if (enrollmentsRepository.existsByStudents_IdAndClasses_IdAndStatusNot(request.getStudentId(), request.getClassId(),Status.Cancelled)) {
            throw new Exception("Bạn đã đăng ký lớp này rồi");
        }

        int currentEnrollment = enrollmentsRepository.countByClasses_IdAndStatus(
                (long) request.getClassId(), Status.Enrolled.name());
        if(currentEnrollment>=courseClass.getMaxStudent())
        {
            throw new IllegalStateException("Lớp học đã đủ số lượng, không thể đăng ký!");
        }
        Enrollments enrollments = Enrollments.builder()
                .students(students)
                .classes(courseClass)
                .status(Status.Enrolled)
                .build();
        return enrollmentsRepository.save(enrollments);
    }
}
