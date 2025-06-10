package com.project.service;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.Appointment;
import com.project.repository.AppointmentRepository;
 

@Service
public class AppointmentService {
 
    @Autowired
    private AppointmentRepository appointmentRepository;
 
    public List<Appointment> getAppointmentsWithFilters(Integer patientUserId, Integer doctorUserId, LocalDate startDate, LocalDate endDate) throws Exception {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
 
        if (patientUserId == null && doctorUserId == null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
 
        return appointmentRepository.findByPatient_User_UserIdAndDoctor_User_UserIdAndAppointmentDateBetween(
                patientUserId, doctorUserId, startDate, endDate);
    }
 
 
    public List<Appointment> getAppointmentsByStatus(Integer userId, Appointment.Status[] status) throws Exception {
        if (userId == null || status == null || status.length == 0) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
 
        return appointmentRepository.findByPatientOrDoctorUserIdAndStatus(userId, status);
    }
 
 
    public Appointment createAppointment(Appointment appointment) throws Exception {
    	if (appointment == null || appointment.getAppointmentDate() == null || appointment.getPatient() == null || appointment.getDoctor() == null || appointment.getAppointmentTime() == null || appointment.getAppointmentTime().isEmpty()||appointment.getStatus()==null) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
         List<Appointment> existingAppointments = appointmentRepository.findByDoctor_DoctorIdAndAppointmentDate(
            appointment.getDoctor().getDoctorId(), appointment.getAppointmentDate());
 
        boolean exists = existingAppointments.stream()
            .anyMatch(a -> a.getPatient().getPatientId().equals(appointment.getPatient().getPatientId()) &&
                           a.getAppointmentTime().equals(appointment.getAppointmentTime()));
 
        if (exists) {
            throw new CustomException(ErrorConstants.APPOINTMENT_ERROR, HttpStatus.CONFLICT);
        }
 
        return appointmentRepository.save(appointment);
    }
 
 
 
 
    public Appointment updateAppointmentPartial(Integer appointmentId, Map<String, Object> updates) throws Exception {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new CustomException(ErrorConstants.APPOINTMENT_ERROR, HttpStatus.NOT_FOUND));
 
        if (updates.containsKey("appointmentDate")) {
            String appointmentDate = (String) updates.get("appointmentDate");
            if (appointmentDate == null || appointmentDate.isEmpty()) {
                throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            appointment.setAppointmentDate(LocalDate.parse(appointmentDate));
        }
        if (updates.containsKey("appointmentTime")) {
            String appointmentTime = (String) updates.get("appointmentTime");
            if (appointmentTime == null || appointmentTime.isEmpty()) {
                throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            appointment.setAppointmentTime(appointmentTime);
        }
        if (updates.containsKey("status")) {
            String status = (String) updates.get("status");
            if (status == null || status.isEmpty()) {
                throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            appointment.setStatus(Appointment.Status.valueOf(status));
        }
        if (updates.containsKey("reason")) {
            String reason = (String) updates.get("reason");
            if (reason == null || reason.isEmpty()) {
                throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            appointment.setReason(reason);
        }
 
        return appointmentRepository.save(appointment);
    }
 
    public void cancelAppointment(Integer appointmentId) throws Exception {
        if (appointmentId == null || appointmentId <= 0) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
 
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new CustomException("Appointment not found", HttpStatus.NOT_FOUND);
        }
 
        appointmentRepository.deleteById(appointmentId);
    }
 
}
 