package com.project.model;

import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
    @SequenceGenerator(name = "doctor_seq", sequenceName = "doctor_seq", allocationSize = 1)
    @Column(name = "doctor_id")
    private Integer doctorId;
 
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
 
    @Column(nullable = false, length = 255)
    private String specialization;

	@Column(nullable = false)
    private Integer experience;
 
    @Column(name = "contact_number", length = 20)
    private String contactNumber;
    
    public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}