package com.sars.repository;

import com.sars.model.AcademicRecord;
import com.sars.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, Long> {
    List<AcademicRecord> findByStudent(StudentProfile student);
}