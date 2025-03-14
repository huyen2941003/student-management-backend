package com.example.student_management_backend.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_backend.dto.request.SearchRequest;
import com.example.student_management_backend.dto.response.search.SearchResponse;
import com.example.student_management_backend.repository.AnnoucementsRepository;
import com.example.student_management_backend.repository.CourseRepository;
import com.example.student_management_backend.repository.ExamsRepository;
import com.example.student_management_backend.repository.GradeRepository;
import com.example.student_management_backend.repository.ScheduleRepository;
import com.example.student_management_backend.repository.UserRepository;

@Service
public class SearchService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ExamsRepository examRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private AnnoucementsRepository annoucementsRepository;
    @Autowired
    private UserRepository userRepository;

    public SearchResponse search(SearchRequest request) {
        SearchResponse response = new SearchResponse();

        switch (request.getType()) {
            case "course":
                response.setCourses(courseRepository.findByNameContainingIgnoreCase(request.getKeyword()));
                break;
            case "schedule":
                response.setSchedules(scheduleRepository.findByCourses_Courses_NameContainingIgnoreCaseAndDate(
                        request.getKeyword(), request.getDate()));
                break;
            case "exam":
                response.setExams(examRepository.findByClasses_Courses_NameContainingIgnoreCaseAndExamDate(
                        request.getKeyword(), request.getDate()));
                break;
            case "grade":
                response.setGrades(gradeRepository.findByStudents_IdAndCourses_NameContainingIgnoreCase(
                        request.getStudentId(), request.getKeyword()));
                break;
            case "announcement": // Thêm trường hợp tìm kiếm thông báo
                response.setAnnouncements(
                        annoucementsRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                                request.getKeyword(), request.getKeyword()));
                break;
            case "account":
                response.setUser(userRepository.findByUsernameContainingIgnoreCase(request.getKeyword()));
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + request.getType());
        }

        return response;
    }
}