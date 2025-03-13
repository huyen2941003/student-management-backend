package com.example.student_management_backend.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Yêu cầu đặt lại mật khẩu");
            message.setText("Để đặt lại mật khẩu, vui lòng nhấp vào liên kết sau: " + resetLink);
            mailSender.send(message);
        } catch (MailAuthenticationException e) {
            throw new RuntimeException("Lỗi xác thực email: " + e.getMessage());
        } catch (MailException e) {
            throw new RuntimeException("Lỗi gửi email: " + e.getMessage());
        }
    }
}