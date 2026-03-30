package com.sars.service;

import com.sars.model.AcademicRecord;
import com.sars.model.StudentProfile;
import com.sars.repository.AcademicRecordRepository;
import com.sars.repository.NotificationRepository;
import com.sars.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentService {

    @Autowired
    private AcademicRecordRepository academicRecordRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationRepository notificationRepository;

    public String assessRisk(StudentProfile student) {
        List<AcademicRecord> records = academicRecordRepository.findByStudent(student);
        if (records.isEmpty()) return "LOW";

        double totalMarks = 0;
        double totalAttendance = 0;
        int totalMissingAssignments = 0;
        int negativeBehaviorCount = 0;

        for (AcademicRecord record : records) {
            totalMarks += record.getMarks();
            totalAttendance += record.getAttendance();
            totalMissingAssignments += record.getMissingAssignments();
            if ("Negative".equalsIgnoreCase(record.getBehavior())) {
                negativeBehaviorCount++;
            }
        }

        double avgMarks = totalMarks / records.size();
        double avgAttendance = totalAttendance / records.size();

        if (avgAttendance < 60 || avgMarks < 40 || negativeBehaviorCount > 2) {
            return "HIGH";
        } else if (avgAttendance < 75 || avgMarks < 60 || totalMissingAssignments > 2) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    public void updateStudentRisk(StudentProfile student) {
        String oldRisk = student.getRiskLevel();
        String riskLevel = assessRisk(student);
        student.setRiskLevel(riskLevel);
        studentProfileRepository.save(student);

        // Send alert if risk is HIGH and it's a new state or recurring
        if ("HIGH".equals(riskLevel) && !"HIGH".equals(oldRisk)) {
            emailService.sendRiskAlert(student.getUser().getEmail(), student.getUser().getFullName(), riskLevel);
        }
    }
}
