package com.project.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Doctor;
import com.project.model.DoctorAvailability;
import com.project.repository.DoctorAvailabilityRepository;
import com.project.repository.DoctorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
 
@Service
@Transactional
public class DoctorAvailabilityService {
 
    @Autowired
    private DoctorAvailabilityRepository availabilityRepository;
 
    @Autowired
    private DoctorRepository doctorRepository;
 
    public DoctorAvailability blockDoctorTime(Integer doctorId, LocalDate date, String startTime, String endTime, String reason) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (!doctorOpt.isPresent()) {
        	throw new CustomException(ErrorConstants.DOC_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
 
        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctor(doctorOpt.get());
        availability.setAvailableDate(date);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
        availability.setBlocked(true);
        availability.setReason(reason);
 
        return availabilityRepository.save(availability);
    }
    
    public List<DoctorAvailability> getDoctorAvailability(Integer doctorId) {

    	List<DoctorAvailability> availabilities = availabilityRepository.findByDoctorDoctorId(doctorId);
    	if (availabilities == null || availabilities.isEmpty()) {
    		throw new CustomException(ErrorConstants.DOC_NOT_FOUND, HttpStatus.NOT_FOUND);
    		}
    	return availabilities;
    }
    
}
 
