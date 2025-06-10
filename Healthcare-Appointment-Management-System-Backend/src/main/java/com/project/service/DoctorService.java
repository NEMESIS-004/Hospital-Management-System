package com.project.service;

import com.project.constants.ErrorConstants;
import com.project.dto.PatientDetails;
import com.project.exception.CustomException;
import com.project.model.Appointment;
import com.project.model.Doctor;
import com.project.model.Patient;
import com.project.repository.AppointmentRepository;
import com.project.repository.DoctorRepository;
import com.project.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public List<Appointment> getTodaysAppointments(Integer doctorId) throws CustomException {
        if (doctorId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        LocalDate today = LocalDate.now();
        return appointmentRepo.findByDoctor_DoctorIdAndAppointmentDate(doctorId, today);
    }

    public void updateAppointmentStatus(Integer appointmentId, Appointment.Status status) throws CustomException {
        if (appointmentId == null || status == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA , HttpStatus.BAD_REQUEST);
        }
        Appointment appt = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new CustomException(ErrorConstants.APPOINTMENT_NOT_FOUND, HttpStatus.NOT_FOUND));
        appt.setStatus(status);
        appointmentRepo.save(appt);
    }

    public List<Appointment> getScheduleView(Integer doctorId) throws CustomException {
        if (doctorId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        return appointmentRepo.findByDoctorDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByFilter(Integer doctorId, LocalDate start, LocalDate end, Appointment.Status status) throws CustomException {
        if (doctorId == null || start == null || end == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        return appointmentRepo.findByDoctorDoctorIdAndAppointmentDateBetweenAndStatus(doctorId, start, end, status);
    }

    public List<PatientDetails> getPastPatients(Integer doctorId) throws CustomException {
        if (doctorId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA , HttpStatus.BAD_REQUEST);
        }
        return appointmentRepo.findPastPatientsByDoctorId(doctorId);
    }

    public String getMedicalHistory(Integer patientId) throws CustomException {
        if (patientId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA , HttpStatus.BAD_REQUEST);
        }
        Patient patient = (Patient) patientRepo.findByPatientId(patientId);
        if (patient == null) {
            throw new CustomException(ErrorConstants.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return patient.getMedicalHistory();
    }

    public Doctor getDoctorProfile(Integer doctorId) throws CustomException {
        if (doctorId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        return doctorRepo.findById(doctorId)
                .orElseThrow(() -> new CustomException(ErrorConstants.DOC_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Doctor getDoctorProfileByUserId(Integer userId) throws CustomException {
        if (userId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        Optional<Doctor> doctorOpt = doctorRepo.findByUser_UserId(userId);
        if (doctorOpt.isEmpty()) {
            throw new CustomException(ErrorConstants.DOCTOR_PROFILE_NOT_FOUND_FOR_USER, HttpStatus.NOT_FOUND);
        }
        return doctorOpt.get();
    }

    public List<Doctor> getDoctorsList() {
        return doctorRepo.findAll();
    }
}
