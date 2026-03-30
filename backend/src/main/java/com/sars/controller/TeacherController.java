package com.sars.controller;

import com.sars.model.AcademicRecord;
import com.sars.model.Intervention;
import com.sars.model.StudentProfile;
import com.sars.repository.AcademicRecordRepository;
import com.sars.repository.InterventionRepository;
import com.sars.repository.StudentProfileRepository;
import com.sars.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private AcademicRecordRepository academicRecordRepository;

    @Autowired
    private InterventionRepository interventionRepository;

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @GetMapping("/students")
    public List<StudentProfile> getAllStudents() {
        return studentProfileRepository.findAll();
    }

    @PostMapping("/records")
    public ResponseEntity<?> addAcademicRecord(@RequestBody AcademicRecord record) {
        AcademicRecord saved = academicRecordRepository.save(record);
        riskAssessmentService.updateStudentRisk(record.getStudent());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/interventions")
    public ResponseEntity<?> assignIntervention(@RequestBody Intervention intervention) {
        return ResponseEntity.ok(interventionRepository.save(intervention));
    }
}
