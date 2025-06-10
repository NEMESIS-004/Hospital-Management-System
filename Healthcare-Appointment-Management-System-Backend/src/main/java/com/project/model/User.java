package com.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
 
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "user_id")
    private Integer userId;
 
    @Column(nullable = false, length = 255)
    private String name;
 
    @Column(nullable = false, unique = true, length = 255)
    private String email;
 
    @Column(name = "password", nullable = false, length = 255)
    private String password;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
 
 

	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
 
    public enum Role {
        PATIENT, DOCTOR
    }
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
}