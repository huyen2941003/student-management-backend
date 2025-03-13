package com.example.student_management_backend.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.util.error.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}") // Đường dẫn mặc định là "uploads" nếu không cấu hình
    private String uploadDir;

    public String saveAvatarFile(MultipartFile file) {
        try {
            // Đường dẫn đến thư mục uploads/avatars
            Path uploadDirPath = Paths.get(uploadDir, "avatars");

            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }

            // Kiểm tra quyền ghi vào thư mục
            checkWritePermission(uploadDirPath);

            // Tạo tên file duy nhất
            String fileName = generateUniqueFileName(file.getOriginalFilename());

            // Đường dẫn đầy đủ đến file
            Path filePath = uploadDirPath.resolve(fileName);

            // Lưu file vào thư mục
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn file
            return filePath.toString();
        } catch (IOException e) {
            throw new FileStorageException("Không thể lưu file avatar: " + e.getMessage(), e);
        }
    }

    private void checkWritePermission(Path directory) throws IOException {
        if (!Files.isWritable(directory)) {
            throw new IOException("Không có quyền ghi vào thư mục: " + directory);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }
}