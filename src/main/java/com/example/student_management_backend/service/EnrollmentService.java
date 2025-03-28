package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.*;
import com.example.student_management_backend.dto.request.EnrollmentsRequest;
import com.example.student_management_backend.dto.response.StudentGradeResponse;
import com.example.student_management_backend.repository.CourseClassRepository;
import com.example.student_management_backend.repository.EnrollmentsRepository;
import com.example.student_management_backend.repository.GradeRepository;
import com.example.student_management_backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private  final EnrollmentsRepository enrollmentsRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final GradeRepository gradeRepository;
    public Enrollments registerCourseClass(EnrollmentsRequest request, Integer studentId) throws Exception {
        Students students = studentRepository.findById(studentId)
                .orElseThrow(
                        ()-> new Exception("Student dont exist")
                );
        CourseClass courseClass = courseClassRepository.findById(request.getClassId())
                .orElseThrow(
                        ()-> new Exception("CourseClass dont exist")
                );

        if (enrollmentsRepository.existsByStudents_IdAndClasses_IdAndStatusNot(studentId, request.getClassId(),Status.Cancelled)) {
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
        Enrollments enrollments1 = enrollmentsRepository.save(enrollments);
        Grades grades = new Grades();
        grades.setCoursesClass(courseClass);
        grades.setGrade(null);
        grades.setSemester(String.valueOf(courseClass.getSemester()));
        grades.setStudents(students);
        grades.setMidtermScore(null);
        grades.setFinalScore(null);
        grades.setTotalScore(null);
        gradeRepository.save(grades);
        return  enrollments1;
    }

    public List<StudentGradeResponse> getStudentGradesByClassId(int classId) {
        List<Object[]> results = enrollmentsRepository.findStudentGradesByClassId(classId);
        return results.stream().map(obj -> new StudentGradeResponse(
                (String) obj[0],  // full_name
                (Integer) obj[1], // classId
                (Integer) obj[2], // gradeId
                (Integer) obj[3], // studentId
                (Double) obj[4],  // midterm_score
                (Double) obj[5],  // final_score
                (Double) obj[6],  // total_score
                (String) obj[7],  // grade
                (Double) obj[8]   // score
        )).collect(Collectors.toList());
    }
}
