package com.example.student_management_backend.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_management_backend.domain.Students;
import com.example.student_management_backend.domain.Departments;
import com.example.student_management_backend.domain.Gender;
import com.example.student_management_backend.domain.Majors;
import com.example.student_management_backend.domain.Status;
import com.example.student_management_backend.dto.request.StudentFullRequest;
import com.example.student_management_backend.dto.request.StudentRequest;
import com.example.student_management_backend.dto.response.student.StudentResponse;
import com.example.student_management_backend.repository.DepartmentsRepository;
import com.example.student_management_backend.repository.MajorsRepository;
import com.example.student_management_backend.service.FileStorageService;
import com.example.student_management_backend.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MajorsRepository majorRepository;

    @Autowired
    private DepartmentsRepository departmentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
        try {
            StudentResponse response = studentService.getStudentById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudent() {
        List<StudentResponse> response = studentService.getAllStudent();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/students/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStudentForStudent(
            @PathVariable Integer id,
            @Valid @ModelAttribute StudentRequest studentRequest,
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
            Students student = new Students();
            student.setFullName(studentRequest.getFullName());
            student.setDob(studentRequest.getDob());
            student.setEmail(studentRequest.getEmail());
            student.setPhone(studentRequest.getPhone());
            student.setAddress(studentRequest.getAddress());

            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = fileStorageService.saveAvatarFile(avatarFile);
                student.setAvatar(avatarPath);
            }

            Students updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(new StudentResponse(updatedStudent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/studentsadmin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStudentByAdmin(
            @PathVariable Integer id,
            @Valid @ModelAttribute StudentFullRequest studentFullRequest,
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
            Students student = new Students();
            student.setFullName(studentFullRequest.getFullName());
            student.setDob(studentFullRequest.getDob());
            student.setEmail(studentFullRequest.getEmail());
            student.setPhone(studentFullRequest.getPhone());
            student.setAddress(studentFullRequest.getAddress());
            student.setGender(studentFullRequest.getGender());
            student.setStatus(studentFullRequest.getStatus());

            Majors major = majorRepository.findByMajorName(studentFullRequest.getMajor())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Major not found with name: " + studentFullRequest.getMajor()));
            student.setMajors(major);

            Departments department = departmentRepository.findByDepartmentName(studentFullRequest.getDepartment())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with name: " + studentFullRequest.getDepartment()));
            student.setDepartments(department);

            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = fileStorageService.saveAvatarFile(avatarFile);
                student.setAvatar(avatarPath);
            }

            Students updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(new StudentResponse(updatedStudent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }
}
