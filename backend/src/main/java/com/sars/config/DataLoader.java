package com.sars.config;

import com.sars.model.*;
import com.sars.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentProfileRepository studentProfileRepository;

    @Autowired
    AcademicRecordRepository academicRecordRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) return;

        // Create Admin
        User admin = new User(null, "admin", passwordEncoder.encode("admin123"), "admin@sars.com", "System Admin", User.Role.ADMIN);
        userRepository.save(admin);

        // Create Teacher
        User teacher = new User(null, "teacher", passwordEncoder.encode("teacher123"), "teacher@sars.com", "Dr. Smith", User.Role.TEACHER);
        userRepository.save(teacher);

        // Create Students
        createStudent("student_low", "S001", "Low Risk Student", "CS", 85.0, 90.0, 0);
        createStudent("student_med", "S002", "Med Risk Student", "Physics", 60.0, 70.0, 3);
        createStudent("student_high", "S003", "High Risk Student", "Math", 35.0, 55.0, 5);
    }

    private void createStudent(String username, String studentId, String name, String course, Double marks, Double attendance, Integer missing) {
        User user = new User(null, username, passwordEncoder.encode("password123"), username + "@sars.com", name, User.Role.STUDENT);
        userRepository.save(user);

        StudentProfile profile = new StudentProfile(null, user, studentId, course, "Science", 4, "LOW", null, null);
        studentProfileRepository.save(profile);

        AcademicRecord record = new AcademicRecord(null, profile, "General", marks, attendance, missing, "Neutral", LocalDateTime.now());
        academicRecordRepository.save(record);
        
        // Update risk level based on logic
        String risk = assess(marks, attendance, missing);
        profile.setRiskLevel(risk);
        studentProfileRepository.save(profile);
    }

    private String assess(Double marks, Double attendance, Integer missing) {
        if (attendance < 60 || marks < 40) return "HIGH";
        if (attendance < 75 || marks < 60 || missing > 2) return "MEDIUM";
        return "LOW";
    }
}
