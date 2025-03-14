package com.example.student_management_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.dto.request.StudentRequest;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.repository.MajorsRepository;
import com.example.student_management_backend.repository.StudentRepository;
import com.example.student_management_backend.service.file.FileStorageService;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MajorsRepository majorsRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // Lấy thông tin sinh viên theo ID
    public StudentResponse getStudentById(Integer id) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));
        return new StudentResponse(student);
    }

    @PreAuthorize("isAuthenticated()")
    public List<StudentResponse> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    // Cập nhật thông tin sinh viên
    @Autowired
    private FileStorageService fileStorageService;

    public StudentResponse updateStudentForStudent(Integer id, StudentRequest studentRequest,
            MultipartFile avatarFile) {
        // Tìm sinh viên theo id
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));

        // Cập nhật thông tin cơ bản
        student.setFullName(studentRequest.getFullName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());

        // Cập nhật avatar nếu có
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarPath = fileStorageService.saveAvatarFile(avatarFile); // Lưu file và trả về đường dẫn
            student.setAvatar(avatarPath);
        }

        studentRepository.save(student);

        return new StudentResponse(student);
    }

    public StudentResponse updateStudentForAdmin(Integer id, StudentRequest studentRequest,
            MultipartFile avatarFile) {

        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));

        student.setFullName(studentRequest.getFullName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarPath = fileStorageService.saveAvatarFile(avatarFile);
            student.setAvatar(avatarPath);
        }

        if (studentRequest.getMajorId() != null) {
            Majors major = majorsRepository.findById(studentRequest.getMajorId())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy chuyên ngành với id: " + studentRequest.getMajorId()));
            student.setMajors(major);
        }

        if (studentRequest.getDepartmentId() != null) {
            Departments department = departmentsRepository.findById(studentRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy khoa với id: " + studentRequest.getDepartmentId()));
            student.setDepartments(department);
        }

        studentRepository.save(student);

        return new StudentResponse(student);
    }

    public Students getStudentByUserId(int userId) {
        return studentRepository.findByUserId(userId);
    }

}