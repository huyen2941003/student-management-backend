package com.example.student_management_backend.util;

import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.repository.UserRepository;
import com.example.student_management_backend.service.LectureService;
import com.example.student_management_backend.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LectureService lectureService;

    public Integer loggedInStudentId() throws Exception {
        Long userId = loggedInUserId();
        return studentService.getStudentByUserId(userId);
    }

    public Lectures loggedInLectureId() throws Exception {
        Long userId = loggedInUserId();
        return lectureService.getLectureByUserId(userId);
    }

    public Long loggedInUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new Exception("Username dont exits "));
        return Long.valueOf(user.getId());
    }

}