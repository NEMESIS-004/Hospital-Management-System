package com.project.model;

import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescription_seq")
    @SequenceGenerator(name = "prescription_seq", sequenceName = "prescription_seq", allocationSize = 1)
    @Column(name = "prescription_id")
    private Integer prescriptionId;
 
    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;
 

	@ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
 
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
 
    @Lob
    @Column(nullable = false)
    private String medication;
 
    @Lob
    @Column(nullable = false)
    private String dosage;
 
    @Lob
    private String instructions;
 
    public Integer getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Integer prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
}