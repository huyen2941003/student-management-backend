package com.example.student_management_backend.controller;

import com.example.student_management_backend.dto.response.AnnouncementResponse;
import com.example.student_management_backend.service.AnnoucementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/annoucements")
public class AnnoucementsController {
    private final AnnoucementsService annoucementsService;

    @GetMapping
    public List<AnnouncementResponse> getAllAnnouncements() {
        return annoucementsService.getAllAnnouncements();
    }
}
