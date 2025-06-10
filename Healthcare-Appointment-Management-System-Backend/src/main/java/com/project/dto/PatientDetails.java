package com.project.dto;


import java.time.LocalDate;

 
import com.project.model.Patient.Gender;
 
import lombok.AllArgsConstructor;

import lombok.Data;
 
 
@Data
@AllArgsConstructor
public class PatientDetails {

	 private Integer patientId;

	 private String name;

	 private Gender gender;

	 private String contactNumber;

	 private LocalDate lastVisitDate;

	 public PatientDetails(Integer patientId, String name, Gender gender, String contactNumber, LocalDate lastVisitDate) {

	        this.patientId = patientId;

	        this.name = name;

	        this.gender = gender;

	        this.contactNumber = contactNumber;

	        this.lastVisitDate = lastVisitDate;

	 }
 
	public Integer getPatientId() {

		return patientId;

	}
 
	public void setPatientId(Integer patientId) {

		this.patientId = patientId;

	}
 
	public String getName() {

		return name;

	}
 
	public void setName(String name) {

		this.name = name;

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
 
	public LocalDate getLastVisitDate() {

		return lastVisitDate;

	}
 
	public void setLastVisitDate(LocalDate lastVisitDate) {

		this.lastVisitDate = lastVisitDate;

	}
 
}
 