package com.sars.controller;

import com.sars.model.AcademicRecord;
import com.sars.model.StudentProfile;
import com.sars.repository.AcademicRecordRepository;
import com.sars.repository.StudentProfileRepository;
import com.sars.service.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private PdfReportService pdfReportService;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private AcademicRecordRepository academicRecordRepository;

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<InputStreamResource> getStudentReport(@PathVariable Long id) {
        StudentProfile student = studentProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<AcademicRecord> records = academicRecordRepository.findByStudent(student);
        ByteArrayInputStream bis = pdfReportService.generateStudentReport(student, records);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student_report_" + student.getStudentId() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
