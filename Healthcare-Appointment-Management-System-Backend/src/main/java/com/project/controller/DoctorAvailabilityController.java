package com.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.DoctorAvailability;
import com.project.service.DoctorAvailabilityService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/doctor/availability")
public class DoctorAvailabilityController {

    private static final Logger log = LoggerFactory.getLogger(DoctorAvailabilityController.class);

    @Autowired
    private DoctorAvailabilityService availabilityService;

    @PostMapping("/block")
    public DoctorAvailability blockTime(
            @RequestParam Integer doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam(required = false) String reason
    ) {
        log.info("Received request to block time for doctorId: {}, date: {}, startTime: {}, endTime: {}, reason: {}", doctorId, date, startTime, endTime, reason);
        try {
            DoctorAvailability blockedAvailability = availabilityService.blockDoctorTime(doctorId, date, startTime, endTime, reason);
            log.info("Successfully blocked time for doctorId: {}, availability ID: {}", doctorId, blockedAvailability.getAvailabilityId());
            return blockedAvailability;
        } catch (CustomException e) {
            log.error("Custom exception while blocking time for doctorId {}: {}", doctorId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while blocking time for doctorId {}: {}", doctorId, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{doctorId}")
    public List<DoctorAvailability> getAvailability(@PathVariable Integer doctorId) {
        log.info("Received request to get availability for doctorId: {}", doctorId);
        try {
            List<DoctorAvailability> availabilities = availabilityService.getDoctorAvailability(doctorId);
            log.info("Retrieved {} availability records for doctorId: {}", availabilities.size(), doctorId);
            return availabilities;
        } catch (CustomException e) {
            log.error("Custom exception while getting availability for doctorId {}: {}", doctorId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while getting availability for doctorId {}: {}", doctorId, e.getMessage(), e);
            throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}