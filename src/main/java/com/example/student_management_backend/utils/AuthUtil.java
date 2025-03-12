//package com.example.student_management_backend.utils;
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthUtil {
//
//    @Autowired
//    IUserRepository userRepository;
//
//
//
//    public Long loggedInUserId(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUserName(authentication.getName())
//                .orElseThrow(() -> new Exception("Username dont exits "));
//        return Long.valueOf(user.getId());
//    }
//
//
//
//}