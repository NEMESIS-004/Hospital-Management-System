package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    List<Doctor> findBySpecialization(String specialization);
    
    Optional<Doctor> findByUser_UserId(Integer userId);
}
