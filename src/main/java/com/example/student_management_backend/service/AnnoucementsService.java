package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Announcements;
import com.example.student_management_backend.domain.User;
import com.example.student_management_backend.dto.request.AnnouncementsRequest;
import com.example.student_management_backend.dto.response.AnnouncementResponse;
import com.example.student_management_backend.repository.AnnoucementsRepository;
import com.example.student_management_backend.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnoucementsService {
    private final AnnoucementsRepository annoucementsRepository;
    private final UserRepository userRepository;
    private final FirebaseMessagingService firebaseMessagingService;


    public List<AnnouncementResponse> getAllAnnouncements() {
        List<Announcements> announcements = annoucementsRepository.findAll();
        return announcements.stream()
                .map(this::mapToResponse)
                .toList();
    }
    private AnnouncementResponse mapToResponse(Announcements announcement) {
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getUser().getUsername()
        );
    }

    public Announcements createAnnouncements(AnnouncementsRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                ()-> new Exception("User dont exist")
        );
        Announcements announcements = new Announcements();
        announcements.setTitle(request.getTitle());
        announcements.setContent(request.getContent());
        announcements.setCreatedAt(LocalDateTime.now());
        announcements.setUser(user);
        Announcements savedAnnouncement = annoucementsRepository.save(announcements);

        String userFcmToken = user.getFcmToken();  // Giả sử bạn đã lưu token FCM của user
        if (userFcmToken != null) {
            try {
                firebaseMessagingService.sendNotification(userFcmToken, request.getTitle(), request.getContent());
            } catch (FirebaseMessagingException e) {
                System.out.println("Lỗi gửi thông báo FCM: " + e.getMessage());
            }
        }
        return savedAnnouncement;
    }

    public void deleteAnnouncement(int id) throws Exception {
        Announcements announcements = annoucementsRepository.findById(id).orElseThrow(
                ()-> new Exception("Announcement dont exist")
        );
        annoucementsRepository.delete(announcements);
    }
}
