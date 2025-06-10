package com.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
 

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    @Column(name = "notification_id")
    private Integer notificationId;
 
    public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
 
    @Lob
    @Column(nullable = false)
    private String message;
 
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status;
 
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
 
    public enum Status {
        UNREAD, READ
    }
}