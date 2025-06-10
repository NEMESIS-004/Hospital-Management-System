package com.project.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_seq", allocationSize = 1)
    @Column(name = "patient_id")
    private Integer patientId;
 
    
    public Integer getPatientId() {
		return patientId;
	}
    
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;


	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
 
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
 
    @Column(name = "contact_number", length = 20)
    private String contactNumber;
 
    @Lob
    @Column(name = "medical_history")
    private String medicalHistory;
 
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}