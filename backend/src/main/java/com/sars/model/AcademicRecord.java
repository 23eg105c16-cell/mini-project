package com.sars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "academic_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile student;

    private String subject;
    private Double marks;
    private Double attendance; // Percentage
    private Integer missingAssignments;
    private String behavior; // Positive, Neutral, Negative
    private LocalDateTime recordedAt;
}
