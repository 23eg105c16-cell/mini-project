package com.sars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String studentId; // Roll number
    private String course;
    private String department;
    private Integer semester;

    private String riskLevel; // LOW, MEDIUM, HIGH

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<AcademicRecord> academicRecords;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Intervention> interventions;
}
