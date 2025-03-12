package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Announcements;
import com.example.student_management_backend.domain.Exams;
import com.example.student_management_backend.dto.request.AnnouncementsRequest;
import com.example.student_management_backend.dto.request.ExamRequest;
import com.example.student_management_backend.dto.response.AnnouncementResponse;
import com.example.student_management_backend.repository.AnnoucementsRepository;
import com.example.student_management_backend.service.AnnoucementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/annoucements")
public class AnnoucementsController {
    private final AnnoucementsService annoucementsService;
    @GetMapping
    public List<AnnouncementResponse> getAllAnnouncements()
    {
        return annoucementsService.getAllAnnouncements();
    }

    @PostMapping("")
    public ResponseEntity<Announcements> createAnnouncements
            (@RequestBody AnnouncementsRequest request) throws Exception {
        Announcements announcements = annoucementsService.createAnnouncements(request);
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnnouncement(@PathVariable int id) throws Exception {
        annoucementsService.deleteAnnouncement(id);
        return ResponseEntity.ok().body("Annoucement deleted successfully");

    }
}
