package com.example.student_management_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.dto.request.LectureFullRequest;
import com.example.student_management_backend.dto.request.LectureRequest;
import com.example.student_management_backend.dto.response.lecture.LectureResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.service.FileStorageService;
import com.example.student_management_backend.service.LectureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LectureController {
    @Autowired
    private LectureService lectureService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DepartmentsRepository departmentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lectures/{id}")
    public ResponseEntity<?> getLectureById(@PathVariable Integer id) {
        try {
            LectureResponse response = lectureService.getLectureById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lectures")
    public ResponseEntity<?> getAllLecture() {
        List<LectureResponse> response = lectureService.getAllLecture();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('LECTURER')")
    @PutMapping(value = "/lectures/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateLectureForLecture(
            @PathVariable Integer id,
            @Valid @ModelAttribute LectureRequest LectureRequest,
            BindingResult bindingResult,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Lectures Lecture = new Lectures();
            Lecture.setFullName(LectureRequest.getFullName());
            Lecture.setDob(LectureRequest.getDob());
            Lecture.setEmail(LectureRequest.getEmail());
            Lecture.setPhone(LectureRequest.getPhone());
            Lecture.setAddress(LectureRequest.getAddress());

            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = fileStorageService.saveAvatarFile(avatarFile);
                Lecture.setAvatar(avatarPath);
            }

            Lectures updatedLecture = lectureService.updateLecture(id, Lecture);
            return ResponseEntity.ok(new LectureResponse(updatedLecture));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/lecturesadmin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateLectureByAdmin(
            @PathVariable Integer id,
            @Valid @ModelAttribute LectureFullRequest LectureFullRequest,
            BindingResult bindingResult,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Lectures Lecture = new Lectures();
            Lecture.setFullName(LectureFullRequest.getFullName());
            Lecture.setDob(LectureFullRequest.getDob());
            Lecture.setEmail(LectureFullRequest.getEmail());
            Lecture.setPhone(LectureFullRequest.getPhone());
            Lecture.setAddress(LectureFullRequest.getAddress());
            Lecture.setGender(LectureFullRequest.getGender());
            Lecture.setStatus(LectureFullRequest.getStatus());

            Departments department = departmentRepository.findByDepartmentName(LectureFullRequest.getDepartment())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with name: " + LectureFullRequest.getDepartment()));
            Lecture.setDepartments(department);

            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = fileStorageService.saveAvatarFile(avatarFile);
                Lecture.setAvatar(avatarPath);
            }

            Lectures updatedLecture = lectureService.updateLecture(id, Lecture);
            return ResponseEntity.ok(new LectureResponse(updatedLecture));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Integer id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}
