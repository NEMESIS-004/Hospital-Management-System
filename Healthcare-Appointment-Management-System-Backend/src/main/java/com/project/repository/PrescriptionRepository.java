package com.project.repository;

import com.project.model.Prescription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	List<Prescription> findByPatient_PatientId(Integer patientId);

	Prescription findByAppointment_AppointmentId(Integer appointmentId);
}
