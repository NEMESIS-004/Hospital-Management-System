package com.project.controller;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Consultation;
import com.project.service.ConsultationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/consultationController")
public class ConsultationController {

    private static final Logger log = LoggerFactory.getLogger(ConsultationController.class);

    @Autowired
    private ConsultationService consultationService;

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Integer id) {
        log.info("Received request to get consultation by ID: {}", id);
        try {
            Consultation consultation = consultationService.getConsultationById(id);
            log.info("Retrieved consultation with ID: {}", id);
            return new ResponseEntity<>(consultation, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting consultation by ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting consultation by ID {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Consultation>> getConsultationsByPatientId(@PathVariable Integer id) {
        log.info("Received request to get consultations by patient ID: {}", id);
        try {
            List<Consultation> consultations = consultationService.getConsultationsByPatientId(id);
            log.info("Retrieved {} consultations for patient ID: {}", consultations.size(), id);
            return new ResponseEntity<>(consultations, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting consultations by patient ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting consultations by patient ID {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Consultation> createConsultation(@RequestBody Consultation consultation) {
        log.info("Received request to create a new consultation: {}", consultation);
        try {
            Consultation savedConsultation = consultationService.saveConsultation(consultation);
            log.info("Consultation created successfully with ID: {}", savedConsultation.getConsultationId());
            return new ResponseEntity<>(savedConsultation, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("Custom exception while creating consultation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while creating consultation: {}", e.getMessage(), e);
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable Integer id, @RequestBody Consultation consultationDetails) {
        log.info("Received request to update consultation with ID: {}, details: {}", id, consultationDetails);
        try {
            Consultation updatedConsultation = consultationService.updateConsultation(id, consultationDetails);
            log.info("Consultation with ID {} updated successfully.", id);
            return new ResponseEntity<>(updatedConsultation, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while updating consultation {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating consultation {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConsultation(@PathVariable Integer id) {
        log.info("Received request to delete consultation with ID: {}", id);
        try {
            consultationService.deleteConsultation(id);
            log.info("Consultation with ID {} deleted successfully.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomException e) {
            log.error("Custom exception while deleting consultation {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while deleting consultation {}: {}", id, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}