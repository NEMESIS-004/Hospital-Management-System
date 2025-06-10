package com.project.controller;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Appointment;
import com.project.service.AppointmentService;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    //use user_id fir both patient and doctor
	@GetMapping("/filter")
	public ResponseEntity<?> getAppointmentsWithFilters(
	    @RequestParam(required = false) Integer patientUserId,
	    @RequestParam(required = false) Integer doctorUserId,
	    @RequestParam LocalDate startDate,
	    @RequestParam LocalDate endDate,
	    HttpServletRequest request) {
	    log.info("Received filter appointments request for patientId: {}, doctorId: {}, startDate: {}, endDate: {}", patientUserId, doctorUserId, startDate, endDate);
	    try {
	        if (startDate.isAfter(endDate)) {
	            log.warn("Invalid date range provided: startDate {} is after endDate {}", startDate, endDate);
	            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
	        }

	        List<Appointment> filteredAppointments = appointmentService.getAppointmentsWithFilters(patientUserId, doctorUserId, startDate, endDate);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Filtered appointments retrieved successfully");
	        response.put("appointments", filteredAppointments);
	        log.info("Successfully retrieved {} filtered appointments.", filteredAppointments.size());
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (CustomException e) {
	        log.error("Custom exception while filtering appointments: {}", e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        log.error("Unexpected error while filtering appointments: {}", e.getMessage(), e);
	        throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}




    @GetMapping("/status")
    public ResponseEntity<?> getAppointmentsByStatus(@RequestParam Integer patientUserId, @RequestParam Appointment.Status[] status) throws Exception {
        log.info("Received request to get appointments by status for patientId: {}, status: {}", patientUserId, status);
        try{
            List<Appointment> appointments = appointmentService.getAppointmentsByStatus(patientUserId, status);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Appointments fetched successfully");
            response.put("appointments", appointments);
            log.info("Successfully fetched {} appointments by status.", appointments.size());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(CustomException e) {
            log.error("Custom exception while fetching appointments by status: {}", e.getMessage());
            throw e;
        }catch (Exception e) {
            log.error("Unexpected error while fetching appointments by status: {}", e.getMessage(), e);
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        log.info("Received request to create a new appointment: {}", appointment);
        try {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            log.info("Appointment created successfully with ID: {}", createdAppointment.getAppointmentId());
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("Custom exception while creating appointment: {}", e.getMessage());
            throw e; 
        } catch (Exception e) {
            log.error("Unexpected error while creating appointment: {}", e.getMessage(), e);
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("update/{appointmentId}")
    public ResponseEntity<?> updateAppointmentPartial(
            @PathVariable Integer appointmentId,
            @RequestBody Map<String, Object> updates) {
        log.info("Received request to partially update appointment with ID: {}, updates: {}", appointmentId, updates);
        try {
            Appointment updatedAppointment = appointmentService.updateAppointmentPartial(appointmentId, updates);
            log.info("Appointment with ID {} updated successfully.", appointmentId);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while updating appointment {}: {}", appointmentId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating appointment {}: {}", appointmentId, e.getMessage(), e);
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Integer appointmentId) {
        log.info("Received request to cancel appointment with ID: {}", appointmentId);
        try {
            appointmentService.cancelAppointment(appointmentId);
            log.info("Appointment with ID {} canceled successfully.", appointmentId);
            return new ResponseEntity<>(Collections.singletonMap("message", "Appointment canceled successfully"), HttpStatus.OK);
        } catch (CustomException e) {
            log.error("Custom exception while canceling appointment {}: {}", appointmentId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while canceling appointment {}: {}", appointmentId, e.getMessage(), e);
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}