package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.dto.PatientDetails;
import com.project.model.Appointment;
import com.project.model.Appointment.Status;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
	
    List<Appointment> findByPatient_User_UserIdAndDoctor_User_UserIdAndAppointmentDateBetween(
            Integer userId1,
            Integer userId2,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Appointment> findByPatient_User_UserIdAndAppointmentDateAfter(
            Integer userId,
            LocalDate currentDate
    );

    List<Appointment> findByPatient_User_UserIdAndAppointmentDateBefore(
            Integer userId,
            LocalDate currentDate
    );
    
//    @Query("SELECT a FROM Appointment a JOIN a.user u WHERE u.userId = :userId AND a.status IN :statuses")
//    List<Appointment> findByPatient_User_UserIdAndStatus( Integer userId, Status[] statuses);
    
    @Query("SELECT a FROM Appointment a " +
            "JOIN a.patient p JOIN p.user pu " +
            "JOIN a.doctor d JOIN d.user du " +
            "WHERE ((:id IS NOT NULL AND pu.userId = :id) OR (:id IS NOT NULL AND du.userId = :id)) " +
            "AND a.status IN :statuses")
     List<Appointment> findByPatientOrDoctorUserIdAndStatus(
             @Param("id") Integer id,
             @Param("statuses") Status[] statuses);

    List<Appointment> findByDoctor_User_UserIdAndAppointmentDateBetween(
            Integer doctorUserId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Appointment> findTop5ByPatient_User_UserIdOrderByAppointmentDateDesc(Integer userId);
    
    List<Appointment> findByDoctorDoctorId(Integer doctorId);
    
    List<Appointment> findByDoctor_DoctorIdAndAppointmentDate(Integer doctorId, LocalDate date);
    
    List<Appointment> findByDoctorDoctorIdAndAppointmentDateBetweenAndStatus(
        Integer doctorId, LocalDate start, LocalDate end, Appointment.Status status);
 
    @Query("SELECT new com.project.dto.PatientDetails( " +
    		"p.patientId, u.name, p.gender, p.contactNumber, MAX(a.appointmentDate)) " +
    		       "FROM Appointment a " +
    		       "JOIN a.patient p " +
    		       "JOIN p.user u " +
    		       "WHERE a.doctor.doctorId = :doctorId " +
    		       "AND a.appointmentDate < CURRENT_DATE " +
    		"GROUP BY p.patientId, u.name, p.gender, p.contactNumber")
     List<PatientDetails> findPastPatientsByDoctorId(@Param("doctorId") Integer doctorId);

        
}
