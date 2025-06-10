package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.model.Notification;
import com.project.model.User;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {

	List<Notification> findByUser(User user);
	
	List<Notification> findByUser_UserIdOrderByCreatedAtDesc(Integer userId);
}
