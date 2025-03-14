package com.example.student_management_backend.service;

import com.example.student_management_backend.domain.Announcements;
import com.example.student_management_backend.dto.response.AnnouncementResponse;
import com.example.student_management_backend.repository.AnnoucementsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnoucementsService {
    private final AnnoucementsRepository annoucementsRepository;

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
                announcement.getUser().getUsername());
    }
}
