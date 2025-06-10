package com.project.controller;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Consultation;
import com.project.model.Prescription;
import com.project.service.PrescriptionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/prescriptionController")
public class PrescriptionController {

    private static final Logger log = LoggerFactory.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Integer id) {
        log.info("Received request to get prescription by ID: {}", id);
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            log.info("Retrieved prescription with ID: {}", id);
            return new ResponseEntity<>(prescription, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting prescription by ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting prescription by ID {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatientId(@PathVariable Integer id) {
        log.info("Received request to get prescriptions by patient ID: {}", id);
        try {
            List<Prescription> prescription = prescriptionService.getPrescriptionsByPatientId(id);
            log.info("Retrieved {} prescriptions for patient ID: {}", prescription.size(), id);
            return new ResponseEntity<>(prescription, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting prescriptions by patient ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting prescriptions by patient ID {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        log.info("Received request to create a new prescription: {}", prescription);
        try {
            Prescription savedPrescription = prescriptionService.savePrescription(prescription);
            log.info("Prescription created successfully with ID: {}", savedPrescription.getPrescriptionId());
            return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("Custom exception while creating prescription: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while creating prescription: {}", e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable("id") Integer id, @RequestBody Prescription prescriptionDetails) {
        log.info("Received request to update prescription with ID: {}, details: {}", id, prescriptionDetails);
        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescriptionDetails);
            log.info("Prescription with ID {} updated successfully.", id);
            return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while updating prescription {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating prescription {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable("id") Integer id) {
        log.info("Received request to delete prescription with ID: {}", id);
        try {
            prescriptionService.deletePrescription(id);
            log.info("Prescription with ID {} deleted successfully.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomException e) {
            log.error("Custom exception while deleting prescription {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while deleting prescription {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}