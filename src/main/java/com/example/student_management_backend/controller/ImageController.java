package com.example.student_management_backend.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.UrlResource;
import com.google.api.client.util.Value;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/avatars/{filename}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String filename) {
        try {
            // Xây dựng đường dẫn đến file ảnh
            Path filePath = Paths.get(uploadDir).resolve("avatars/" + filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Kiểm tra xem file có tồn tại và có thể đọc được không
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Hoặc MediaType.IMAGE_JPEG tùy loại ảnh
                        .body(resource);
            } else {
                // Trả về thông báo lỗi chi tiết nếu file không tồn tại hoặc không thể đọc
                throw new RuntimeException("File not found or cannot be read: " + filename);
            }
        } catch (Exception e) {
            // Log lỗi và trả về thông báo chi tiết
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to load image: " + e.getMessage());
        }
    }
}