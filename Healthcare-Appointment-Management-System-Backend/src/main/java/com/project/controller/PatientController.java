package com.project.controller;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Patient;
import com.project.service.PatientService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        log.info("Received request to add a new patient: {}", patient);
        try {
            patientService.savePatient(patient);
            log.info("Patient created successfully with userId: {}", patient.getPatientId());
            return new ResponseEntity<>("PATIENT_" + ErrorConstants.CREATED, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("Custom exception while adding patient: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while adding patient: {}", e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Patient> getPatientProfile(@PathVariable Integer userId) {
        log.info("Received request to get patient profile for userId: {}", userId);
        try {
            Patient patient = patientService.getPatientProfile(userId);
            log.info("Retrieved patient profile for userId: {}", userId);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting patient profile for userId {}: {}", userId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting patient profile for userId {}: {}", userId, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medicalHistory/{userId}")
    public ResponseEntity<String> getMedicalHistory(@PathVariable Integer userId) {
        log.info("Received request to get medical history for userId: {}", userId);
        try {
            String medicalHistory = patientService.getMedicalHistory(userId);
            log.info("Retrieved medical history for userId: {}", userId);
            return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting medical history for userId {}: {}", userId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting medical history for userId {}: {}", userId, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}