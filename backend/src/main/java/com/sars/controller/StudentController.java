package com.sars.controller;

import com.sars.model.StudentProfile;
import com.sars.model.User;
import com.sars.repository.StudentProfileRepository;
import com.sars.repository.UserRepository;
import com.sars.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return ResponseEntity.ok(studentProfileRepository.findByUser(user));
    }

    @GetMapping("/risk/{studentId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> getStudentRisk(@PathVariable String studentId) {
        StudentProfile student = studentProfileRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(riskAssessmentService.assessRisk(student));
    }
}
