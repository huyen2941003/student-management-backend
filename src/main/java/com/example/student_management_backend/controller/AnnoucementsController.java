package com.example.student_management_backend.controller;

import com.example.student_management_backend.domain.Announcements;
import com.example.student_management_backend.dto.request.AnnouncementsRequest;
import com.example.student_management_backend.dto.response.AnnouncementResponse;
import com.example.student_management_backend.service.AnnoucementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/annoucements")
public class AnnoucementsController {
    private final AnnoucementsService annoucementsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<AnnouncementResponse> getAllAnnouncements() {
        return annoucementsService.getAllAnnouncements();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @PostMapping("")
    public ResponseEntity<Announcements> createAnnouncements(@RequestBody AnnouncementsRequest request)
            throws Exception {
        Announcements announcements = annoucementsService.createAnnouncements(request);
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnnouncement(@PathVariable int id) throws Exception {
        annoucementsService.deleteAnnouncement(id);
        return ResponseEntity.ok().body("Annoucement deleted successfully");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<Page<Announcements>> searchAnnouncements(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) LocalDateTime createdAtFrom,
            @RequestParam(required = false) LocalDateTime createdAtTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));

        Page<Announcements> announcements = annoucementsService.searchAnnouncements(
                title, content, username,
                createdAtFrom, createdAtTo,
                pageable);

        return ResponseEntity.ok(announcements);
    }
}
