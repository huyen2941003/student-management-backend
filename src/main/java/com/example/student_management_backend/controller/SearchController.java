package com.example.student_management_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_backend.domain.SearchRequest;
import com.example.student_management_backend.dto.response.search.SearchResponse;
import com.example.student_management_backend.service.search.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest request) {
        SearchResponse response = searchService.search(request);
        return ResponseEntity.ok(response);
    }
}