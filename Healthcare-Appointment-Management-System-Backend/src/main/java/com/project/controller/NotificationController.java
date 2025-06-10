package com.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Notification;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.NotificationService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class NotificationController {
    
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/notifications")
	public ResponseEntity<Notification> createNotification(@RequestParam Integer userId, @RequestParam String message){
		
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			
			return ResponseEntity.badRequest().body(null);
			}
		User user = userOpt.get();
		try {
			Notification notification = notificationService.createNotification(user, message);
			return ResponseEntity.ok(notification);
			} catch (Exception e) {
				return ResponseEntity.status(500).body(null);
				}
	}
	
		@GetMapping("/notifications")
		public ResponseEntity<List<Notification>> getNotificationsByUser(@RequestParam Integer userId) {
			
			Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
        	 return ResponseEntity.badRequest().body(null);
        	}
        User user = userOpt.get();
        List<Notification> notifications = notificationService.getNotificationsByUser(user);
        
        return ResponseEntity.ok(notifications); }
		
		@GetMapping("/notifications/{id}")
		public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
			
			Optional<Notification> notification = notificationService.getNotificationById(id);
			return notification.map(ResponseEntity :: ok).orElseGet(() -> {
				
				return ResponseEntity.notFound().build();
				});
			}
		@PostMapping("/notifications/{id}/read")
		public ResponseEntity<Notification> markAsRead(@PathVariable Integer id) {
			Notification notification = notificationService.markAsRead(id);
			return notification != null ? ResponseEntity.ok(notification) : ResponseEntity.notFound().build();
			}
		@DeleteMapping("/notifications/{id}")
		public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
			notificationService.deleteNotification(id);
			return ResponseEntity.noContent().build(); 
			}

}






