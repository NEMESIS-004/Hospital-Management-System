package com.project.model;
 
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "doctor_availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorAvailability {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_availability_seq")
    @SequenceGenerator(name = "doctor_availability_seq", sequenceName = "doctor_availability_seq", allocationSize = 1)
    @Column(name = "availability_id")
    private Integer availabilityId;
 
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
 
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;
 
    @Column(name = "start_time", nullable = false)
    private String startTime;
 
    @Column(name = "end_time", nullable = false)
    private String endTime;
 
    @Column(name = "is_blocked", nullable = false)
    private boolean blocked;
 
    @Column(name = "reason")
    private String reason;

	public Integer getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(Integer availabilityId) {
		this.availabilityId = availabilityId;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
} 