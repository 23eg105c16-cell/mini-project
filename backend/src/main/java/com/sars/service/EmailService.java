package com.sars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRiskAlert(String to, String studentName, String riskLevel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alerts@sars-system.com");
        message.setTo(to);
        message.setSubject("URGENT: Academic Risk Alert - " + studentName);
        message.setText("Dear User,\n\nThis is an automated alert from the SARS System.\n\n" +
                "Student " + studentName + " has been identified as " + riskLevel + " RISK based on their recent academic performance and attendance.\n\n" +
                "Please log in to the dashboard to review the details and assign necessary interventions.\n\n" +
                "Best regards,\nSARS System Team");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
