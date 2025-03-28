package com.example.student_management_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.student_management_backend.domain.Lectures;
import com.example.student_management_backend.dto.response.lecture.LectureResponse;
import com.example.student_management_backend.repository.LectureRepository;
import com.example.student_management_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class LectureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private FileStorageService fileStorageService;

    LectureService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy thông tin sinh viên theo ID
    public LectureResponse getLectureById(Integer id) {
        Lectures Lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có sinh viên nào có id: " + id));
        return new LectureResponse(Lecture);
    }

    public List<LectureResponse> getAllLecture() {
        return lectureRepository.findAll().stream()
                .map(LectureResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Lectures updateLecture(Integer id, Lectures updatedLecture) {
        return lectureRepository.findById(id)
                .map(Lecture -> {
                    // Cập nhật thông tin cơ bản
                    Lecture.setFullName(updatedLecture.getFullName());
                    Lecture.setDob(updatedLecture.getDob());
                    Lecture.setEmail(updatedLecture.getEmail());
                    Lecture.setPhone(updatedLecture.getPhone());
                    Lecture.setAddress(updatedLecture.getAddress());

                    Lecture.setGender(Lecture.getGender());
                    Lecture.setStatus(Lecture.getStatus());
                    // Cập nhật avatar nếu có
                    if (updatedLecture.getAvatar() != null) {
                        Lecture.setAvatar(updatedLecture.getAvatar());
                    }

                    return lectureRepository.save(Lecture);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Lecture not found with id " + id));
    }

    @Transactional
    public Lectures updateLectureByAdmin(Integer id, Lectures updateLectureByAdmin) {
        return lectureRepository.findById(id)
                .map(Lecture -> {
                    // Cập nhật thông tin cơ bản
                    Lecture.setFullName(updateLectureByAdmin.getFullName());
                    Lecture.setDob(updateLectureByAdmin.getDob());
                    Lecture.setEmail(updateLectureByAdmin.getEmail());
                    Lecture.setPhone(updateLectureByAdmin.getPhone());
                    Lecture.setAddress(updateLectureByAdmin.getAddress());

                    Lecture.setGender(updateLectureByAdmin.getGender());
                    Lecture.setStatus(updateLectureByAdmin.getStatus());
                    Lecture.setDepartments(updateLectureByAdmin.getDepartments());

                    // Cập nhật avatar nếu có
                    if (updateLectureByAdmin.getAvatar() != null) {
                        Lecture.setAvatar(updateLectureByAdmin.getAvatar());
                    }

                    return lectureRepository.save(Lecture);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Lecture not found with id " + id));
    }

    @Transactional
    public void deleteLecture(Integer lectureId) {
        Optional<Integer> userIdOpt = lectureRepository.findUserIdByLectureId(lectureId);

        lectureRepository.hardDelete(lectureId);

        userIdOpt.ifPresent(userId -> userRepository.hardDeleteUser(userId));
    }

    public Lectures getLectureByUserId(Long userId) {
        return lectureRepository.findByUserId(userId);
    }
}