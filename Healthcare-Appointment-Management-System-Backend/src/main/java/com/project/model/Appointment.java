package com.project.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq")
    @SequenceGenerator(name = "appointment_seq", sequenceName = "appointment_seq", allocationSize = 1)
    @Column(name = "appointment_id")
    private Integer appointmentId;
 
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
 
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
   
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;
    
    @JsonFormat(pattern = "HH:mm")
    @Column(name = "appointment_time",nullable=false)
    private String appointmentTime;
 
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;
 
    @Lob
    private String reason;
 
    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED, RESCHEDULED, BLOCKED
    }
    
    public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}


	
}