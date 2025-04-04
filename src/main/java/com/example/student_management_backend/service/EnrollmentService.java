package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.*;
import com.example.student_management_backend.dto.request.EnrollmentsRequest;
import com.example.student_management_backend.dto.response.StudentGradeResponse;
import com.example.student_management_backend.exception.CustomException;
import com.example.student_management_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private  final EnrollmentsRepository enrollmentsRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final GradeRepository gradeRepository;
    private final ClassScheduleRepository classScheduleRepository;
    public Enrollments registerCourseClass(EnrollmentsRequest request, Integer studentId) {
        // Tìm học sinh
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student does not exist"));

        // Tìm lớp học
        CourseClass courseClass = courseClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Course class does not exist"));

        // Kiểm tra nếu học sinh đã đăng ký lớp học này với trạng thái khác 'Cancelled'
        boolean alreadyEnrolled = enrollmentsRepository.existsByStudents_IdAndClasses_IdAndStatusNot(
                studentId, request.getClassId(), Status.Cancelled);
        if (alreadyEnrolled) {
            throw new CustomException("Bạn đã đăng ký lớp này rồi");
        }

        // Kiểm tra số lượng sinh viên trong lớp
        long currentEnrollment = enrollmentsRepository.countByClasses_IdAndStatus(
                (long) request.getClassId(), Status.Enrolled.name());
        if (currentEnrollment >= courseClass.getMaxStudent()) {
            throw new CustomException("Lớp học đã đủ số lượng, không thể đăng ký!");
        }

        // Kiểm tra trùng lịch học - Phương pháp cải tiến
        List<Enrollments> existingEnrollments = enrollmentsRepository.findByStudents_IdAndStatus(studentId, Status.Enrolled);

        // Lấy tất cả lịch học của lớp mới
        List<Schedule> newClassSchedules = courseClass.getSchedules();

        // Kiểm tra từng lịch học của lớp mới với các lớp đã đăng ký
        for (Enrollments enrollment : existingEnrollments) {
            CourseClass existingClass = enrollment.getClasses();
            List<Schedule> existingSchedules = existingClass.getSchedules();

            // So sánh từng cặp lịch học
            for (Schedule newSchedule : newClassSchedules) {
                for (Schedule existingSchedule : existingSchedules) {
                    // Kiểm tra trùng lặp về ngày trong tuần (từ các ClassSchedule)
                    boolean sameDayOfWeek = hasSameDayOfWeek(newSchedule, existingSchedule);

                    if (sameDayOfWeek) {
                        // Kiểm tra trùng lặp về thời gian
                        boolean timeOverlap = isTimeOverlapping(
                                newSchedule.getStartTime(),
                                newSchedule.getEndTime(),
                                existingSchedule.getStartTime(),
                                existingSchedule.getEndTime()
                        );

                        if (timeOverlap) {
                            // Xác định tên ngày để hiển thị thông báo lỗi
                            String dayName = getDayNameFromSchedules(newSchedule, existingSchedule);
                            String timeInfo = existingSchedule.getStartTime() + " - " + existingSchedule.getEndTime();
                            throw new CustomException("Bạn đã có lớp học vào khung giờ " +
                                    timeInfo + " vào " + dayName);
                        }
                    }
                }
            }
        }

        // Đăng ký lớp học mới
        Enrollments enrollment = Enrollments.builder()
                .students(student)
                .classes(courseClass)
                .status(Status.Enrolled)
                .build();
        Enrollments savedEnrollment = enrollmentsRepository.save(enrollment);

        // Tạo bản ghi điểm mới
        Grades grades = new Grades();
        grades.setCoursesClass(courseClass);
        grades.setStudents(student);
        grades.setSemester(String.valueOf(courseClass.getSemester()));
        gradeRepository.save(grades);

        return savedEnrollment;
    }

    /**
     * Kiểm tra xem hai lịch học có cùng ngày trong tuần không
     */
    private boolean hasSameDayOfWeek(Schedule schedule1, Schedule schedule2) {
        // Lấy danh sách các ClassSchedule cho mỗi lịch học
        List<ClassSchedule> classSchedules1 = getClassSchedulesForSchedule(schedule1);
        List<ClassSchedule> classSchedules2 = getClassSchedulesForSchedule(schedule2);

        // So sánh từng cặp ClassSchedule
        for (ClassSchedule cs1 : classSchedules1) {
            for (ClassSchedule cs2 : classSchedules2) {
                if (cs1.getDayOfWeek().getDayName().equals(cs2.getDayOfWeek().getDayName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Lấy danh sách ClassSchedule cho một Schedule
     */
    private List<ClassSchedule> getClassSchedulesForSchedule(Schedule schedule) {
        // Bạn cần thêm Repository cho ClassSchedule hoặc một phương thức tìm kiếm
        return classScheduleRepository.findByScheduleId(schedule.getId());
    }

    /**
     * Kiểm tra xem hai khoảng thời gian có chồng chéo không
     */
    private boolean isTimeOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        // Hai khoảng thời gian chồng chéo nếu:
        // - Thời gian bắt đầu của khoảng 1 nhỏ hơn thời gian kết thúc của khoảng 2 VÀ
        // - Thời gian kết thúc của khoảng 1 lớn hơn thời gian bắt đầu của khoảng 2
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    /**
     * Lấy tên ngày trong tuần từ hai lịch học
     */
    private String getDayNameFromSchedules(Schedule schedule1, Schedule schedule2) {
        // Lấy ClassSchedule đầu tiên từ mỗi lịch học và trả về tên ngày
        List<ClassSchedule> classSchedules1 = getClassSchedulesForSchedule(schedule1);
        List<ClassSchedule> classSchedules2 = getClassSchedulesForSchedule(schedule2);

        // Ưu tiên lấy từ lịch học thứ nhất
        if (!classSchedules1.isEmpty()) {
            return classSchedules1.get(0).getDayOfWeek().getDayName().toString();
        } else if (!classSchedules2.isEmpty()) {
            return classSchedules2.get(0).getDayOfWeek().getDayName().toString();
        }

        return "không xác định";
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
