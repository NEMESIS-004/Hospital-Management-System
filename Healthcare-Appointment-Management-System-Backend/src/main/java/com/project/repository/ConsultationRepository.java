package com.project.repository;

import com.project.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
	List<Consultation> findByPatient_PatientId(Integer patientId);

	Consultation findByAppointment_AppointmentId(Integer appointmentId);
}
