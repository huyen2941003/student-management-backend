package com.example.student_management_backend.utils;



import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    public Integer loggedInStudentId() throws Exception {
        Long userId = loggedInUserId();
        return studentRepository.findByUserId(Math.toIntExact(userId)).getId();
    }

    public Long loggedInUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new Exception("Username dont exits "));
        return Long.valueOf(user.getId());
    }



}