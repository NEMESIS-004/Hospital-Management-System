package com.project.service;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Prescription;
import com.project.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription getPrescriptionById(Integer id) {
    	 return prescriptionRepository.findById(id)
                 .orElseThrow(() -> new CustomException(ErrorConstants.PRESCRIPTION_NOT_FOUND, HttpStatus.NOT_FOUND));
    	 }
    
    public List<Prescription> getPrescriptionsByPatientId(Integer patientId) {
    	List<Prescription> prescriptions = prescriptionRepository.findByPatient_PatientId(patientId);
        if (prescriptions.isEmpty()) {
            throw new CustomException(ErrorConstants.PRESCRIPTION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return prescriptions;
    }
    
    public Prescription savePrescription(Prescription prescription) {
    	if ( prescription.getAppointment() == null || prescription.getDoctor() == null ||
    			prescription.getPatient() == null ) {
                throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            Prescription existingPrescription = prescriptionRepository.findByAppointment_AppointmentId(prescription.getAppointment().getAppointmentId());
            if (existingPrescription!=null) {
                throw new CustomException(ErrorConstants.DUPLICATE_PRESCRIPTION, HttpStatus.CONFLICT);
            }
            return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Integer id, Prescription prescriptionDetails) {
        Prescription prescription = prescriptionRepository.findById(id)
        		.orElseThrow(() -> new CustomException(ErrorConstants.PRESCRIPTION_NOT_FOUND, HttpStatus.NOT_FOUND));
        prescription.setMedication(prescriptionDetails.getMedication());
        prescription.setDosage(prescriptionDetails.getDosage());
        prescription.setInstructions(prescriptionDetails.getInstructions());
        return prescriptionRepository.save(prescription);
    }
    
    public void deletePrescription(Integer id) {
    	prescriptionRepository.findById(id)
        	.orElseThrow(() -> new CustomException(ErrorConstants.PRESCRIPTION_NOT_FOUND, HttpStatus.NOT_FOUND));
    	prescriptionRepository.deleteById(id);
    }
}