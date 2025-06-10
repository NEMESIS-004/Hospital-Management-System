package com.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.model.Patient;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
	Patient findByPatientId(Integer patientId);
	
	Patient findByUser_UserId(Integer userId);
	
	@Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.doctorId = :doctorId ORDER BY a.appointmentDate DESC")
    List<Patient> findRecentPatientsByDoctor(@Param("doctorId") Integer doctorId);
  
    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.doctorId = :doctorId AND a.appointmentDate < CURRENT_DATE")
    List<Patient> findPastPatientsByDoctor(@Param("doctorId") Integer doctorId);
}
