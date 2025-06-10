package com.project.service;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Patient;
import com.project.repository.PatientRepository;
import com.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;
    
    @Autowired
    private UserRepository userRepo;

    public Patient savePatient(Patient patient) throws CustomException {
        if (patient.getUser() == null || patient.getUser().getUserId() == null ||
            patient.getDateOfBirth() == null || patient.getGender() == null ||
            patient.getContactNumber() == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        if (!userRepo.existsById(patient.getUser().getUserId())) {
            throw new CustomException(ErrorConstants.INVALID_USER, HttpStatus.BAD_REQUEST);
        }

		Patient existingPatient = patientRepo.findByUser_UserId(patient.getUser().getUserId());
			if (existingPatient != null) {
				throw new CustomException(ErrorConstants.DUPLICATE_PATIENT, HttpStatus.CONFLICT);
			}
        return patientRepo.save(patient);
    }

    public Patient getPatientProfile(Integer userId) throws CustomException {
        Patient patient = patientRepo.findByUser_UserId(userId);
        if (patient == null) {
            throw new CustomException(ErrorConstants.INVALID_USER, HttpStatus.NOT_FOUND);
        }
        patient.getUser().setPassword("********"); 
        return patient;
    }

    public String getMedicalHistory(Integer userId) throws CustomException {
        Patient patient = patientRepo.findByUser_UserId(userId);
        if (patient == null) {
            throw new CustomException(ErrorConstants.INVALID_USER, HttpStatus.NOT_FOUND);
        }
        return patient.getMedicalHistory();
    }
}
