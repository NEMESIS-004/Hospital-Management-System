package com.project.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.DoctorAvailability;

import java.time.LocalDate;
import java.util.List;
 
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Integer> {
 
    boolean existsByDoctorDoctorIdAndAvailableDateAndStartTimeAndEndTime(
            Integer doctorId,
            LocalDate date,
            String startTime,
            String endTime
    );
 
    List<DoctorAvailability> findByDoctorDoctorIdAndAvailableDate(Integer doctorId, LocalDate date);

	List<DoctorAvailability> findByDoctorDoctorId(Integer doctorId);
}