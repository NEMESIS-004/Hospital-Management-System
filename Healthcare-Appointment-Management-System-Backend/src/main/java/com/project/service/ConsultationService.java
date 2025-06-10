package com.project.service;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Consultation;
import com.project.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation getConsultationById(Integer id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorConstants.CONSULTATION_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public List<Consultation> getConsultationsByPatientId(Integer patientId) {
        List<Consultation> consultations = consultationRepository.findByPatient_PatientId(patientId);
        if (consultations.isEmpty()) {
            throw new CustomException(ErrorConstants.CONSULTATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return consultations;
    }

    public Consultation saveConsultation(Consultation consultation) throws CustomException {
        if (consultation.getAppointment() == null || consultation.getDoctor() == null ||
            consultation.getPatient() == null || consultation.getPatientDescription() == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        Consultation existingConsultation = consultationRepository.findByAppointment_AppointmentId(consultation.getAppointment().getAppointmentId());
        if (existingConsultation!=null) {
            throw new CustomException(ErrorConstants.DUPLICATE_CONSULTATION, HttpStatus.CONFLICT);
        }
        return consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(Integer id, Consultation consultationDetails) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorConstants.CONSULTATION_NOT_FOUND, HttpStatus.NOT_FOUND));
        consultation.setPatientDescription(consultationDetails.getPatientDescription());
        consultation.setDiagnosis(consultationDetails.getDiagnosis());
        consultation.setTreatmentPlan(consultationDetails.getTreatmentPlan());
        return consultationRepository.save(consultation);
    }
    
    public void deleteConsultation(Integer id) {
        consultationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorConstants.CONSULTATION_NOT_FOUND, HttpStatus.NOT_FOUND));
        consultationRepository.deleteById(id);
    }
}

