package com.project.controller;

import com.project.dto.PatientDetails;
import com.project.exception.CustomException;
import com.project.model.Appointment;
import com.project.model.Doctor;
import com.project.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{doctorId}/appointments/today")
    public ResponseEntity<List<Appointment>> getTodaysAppointments(@PathVariable Integer doctorId) {
        log.info("Received request to get today's appointments for doctorId: {}", doctorId);
        try {
            List<Appointment> appointments = doctorService.getTodaysAppointments(doctorId);
            log.info("Retrieved {} appointments for doctorId: {}", appointments.size(), doctorId);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting today's appointments: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting today's appointments: {}", e.getMessage(), e);
            throw new CustomException("Failed to retrieve today's appointments.", HttpStatus.INTERNAL_SERVER_ERROR); // Use a descriptive message
        }
    }

    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        log.info("Received request to add doctor: {}", doctor);
        try {
            Doctor savedDoctor = doctorService.saveDoctor(doctor);
            log.info("Doctor added successfully with doctorId: {}", savedDoctor.getDoctorId());
            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("Custom exception while adding doctor: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while adding doctor: {}", e.getMessage(), e);
            throw new CustomException("Failed to add doctor.", HttpStatus.INTERNAL_SERVER_ERROR); // Descriptive message
        }
    }

    @PatchMapping("/appointments/{appointmentId}/status")
    public ResponseEntity<String> updateAppointmentStatus(
            @PathVariable Integer appointmentId,
            @RequestParam Appointment.Status status) {
        log.info("Received request to update appointment status for appointmentId: {}, status: {}", appointmentId, status);
        try {
            doctorService.updateAppointmentStatus(appointmentId, status);
            log.info("Appointment status updated successfully for appointmentId: {}", appointmentId);
            return new ResponseEntity<>("Appointment status updated successfully.", HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while updating appointment status: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating appointment status: {}", e.getMessage(), e);
            throw new CustomException("Failed to update appointment status.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{doctorId}/schedule")
    public ResponseEntity<List<Appointment>> getScheduleView(@PathVariable Integer doctorId) {
        log.info("Received request to get schedule view for doctorId: {}", doctorId);
        try {
            List<Appointment> schedule = doctorService.getScheduleView(doctorId);
            log.info("Retrieved {} appointments for doctorId: {}", schedule.size(), doctorId);
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting schedule view: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting schedule view: {}", e.getMessage(), e);
            throw new CustomException("Failed to retrieve schedule.", HttpStatus.INTERNAL_SERVER_ERROR);        }
    }

    @GetMapping("/{doctorId}/past-patients")
    public ResponseEntity<List<PatientDetails>> getPastPatients(@PathVariable Integer doctorId) {
        log.info("Received request to get past patients for doctorId: {}", doctorId);
        try {
            List<PatientDetails> patients = doctorService.getPastPatients(doctorId);
            log.info("Retrieved {} past patients for doctorId: {}", patients.size(), doctorId);
            return new ResponseEntity<>(patients, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting past patients: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting past patients: {}", e.getMessage(), e);
            throw new CustomException("Failed to retrieve past patients.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medical-history/{patientId}")
    public ResponseEntity<String> getMedicalHistory(@PathVariable Integer patientId) {
        log.info("Received request to get medical history for patientId: {}", patientId);
        try {
            String medicalHistory = doctorService.getMedicalHistory(patientId);
            log.info("Retrieved medical history for patientId: {}", patientId);
            return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting medical history: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting medical history: {}", e.getMessage(), e);
            throw new CustomException("Failed to retrieve medical history.", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @GetMapping("/user/{userId}/doctor/profile")
    public ResponseEntity<Doctor> getDoctorProfileByUserId(@PathVariable Integer userId) {
        log.info("Received request to get doctor profile for userId: {}", userId);
        try {
            Doctor doctor = doctorService.getDoctorProfileByUserId(userId);
            log.info("Retrieved doctor profile for userId: {}", userId);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting doctor profile: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting doctor profile: {}", e.getMessage(), e);
            throw new CustomException("Failed to retrieve doctor profile.", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        log.info("Received request to get all doctors");
        try {
            List<Doctor> doctorList = doctorService.getDoctorsList();
            log.info("Retrieved {} doctors", doctorList.size());
            return new ResponseEntity<>(doctorList, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while getting all doctors: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting all doctors: {}", e.getMessage(), e);
            throw new CustomException("Failed to retrieve doctors list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

