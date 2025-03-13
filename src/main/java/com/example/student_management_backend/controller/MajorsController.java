package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.student_management_backend.dto.request.MajorsRequest;
import com.example.student_management_backend.dto.response.major.MajorsResponse;
import com.example.student_management_backend.service.MajorsService;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
public class MajorsController {

    @Autowired
    private MajorsService majorsService;

    @PostMapping
    public ResponseEntity<MajorsResponse> createMajor(@RequestBody MajorsRequest request) {
        MajorsResponse response = majorsService.createMajor(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MajorsResponse>> getAllMajors() {
        List<MajorsResponse> responses = majorsService.getAllMajors();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorsResponse> getMajorById(@PathVariable Integer id) {
        MajorsResponse response = majorsService.getMajorById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorsResponse> updateMajor(@PathVariable Integer id, @RequestBody MajorsRequest request) {
        MajorsResponse response = majorsService.updateMajor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable Integer id) {
        majorsService.deleteMajor(id);
        return ResponseEntity.noContent().build();
    }
}