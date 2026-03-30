package com.sars.repository;

import com.sars.model.Intervention;
import com.sars.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findByStudent(StudentProfile student);
}