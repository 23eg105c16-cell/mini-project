package com.sars.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sars.model.AcademicRecord;
import com.sars.model.StudentProfile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    public ByteArrayInputStream generateStudentReport(StudentProfile student, List<AcademicRecord> records) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Font styles
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Report Header
            Paragraph header = new Paragraph("Student Academic Risk Report", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" "));

            // Student Info
            document.add(new Paragraph("Student Name: " + student.getUser().getFullName(), normalFont));
            document.add(new Paragraph("Student ID: " + student.getStudentId(), normalFont));
            document.add(new Paragraph("Course: " + student.getCourse(), normalFont));
            document.add(new Paragraph("Risk Level: " + student.getRiskLevel(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, student.getRiskLevel().equals("HIGH") ? java.awt.Color.RED : java.awt.Color.BLACK)));
            document.add(new Paragraph(" "));

            // Academic Records Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table Header
            String[] columns = {"Subject", "Marks", "Attendance", "Missing Assignments"};
            for (String column : columns) {
                PdfPCell cell = new PdfPCell(new Phrase(column, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Table Body
            for (AcademicRecord record : records) {
                table.addCell(record.getSubject());
                table.addCell(String.valueOf(record.getMarks()));
                table.addCell(record.getAttendance() + "%");
                table.addCell(String.valueOf(record.getMissingAssignments()));
            }

            document.add(table);

            // Summary section
            document.add(new Paragraph(" "));
            document.add(new Paragraph("System Recommendations:", subHeaderFont));
            String recommendation = student.getRiskLevel().equals("HIGH") ? 
                    "Urgent intervention required. Schedule mentoring sessions and extra classes." :
                    student.getRiskLevel().equals("MEDIUM") ? 
                    "Monitor closely. Provide additional resources." : 
                    "Satisfactory progress. Continue monitoring.";
            document.add(new Paragraph(recommendation, normalFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
