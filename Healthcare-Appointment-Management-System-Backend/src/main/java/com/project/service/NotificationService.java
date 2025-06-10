package com.project.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Appointment;
import com.project.model.DoctorAvailability;
import com.project.model.Notification;
import com.project.model.User;
import com.project.repository.NotificationRepository;

import jakarta.mail.MessagingException;

import com.itextpdf.text.DocumentException;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PdfService pdfService;

	public Notification createNotification(User user, String message) throws DocumentException, IOException, MessagingException {
		Notification notification = new Notification();
		notification.setUser(user);
		notification.setMessage(message);
		notification.setStatus(Notification.Status.UNREAD);
		Notification savedNotification = notificationRepository.save(notification);
		byte[] pdf = pdfService.generateNotificationPDF(savedNotification);
		sendEmailNotification(user.getEmail(), "New Notification", message, pdf);
		return savedNotification;
	}

	public List<Notification> getNotificationsByUser(User user) {
		return notificationRepository.findByUser(user);
	}

	public Optional<Notification> getNotificationById(Integer notificationId) {
		return notificationRepository.findById(notificationId);
	}


	public Notification markAsRead(Integer notificationId) {
		Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);
		if (notificationOpt.isPresent()) {
			Notification notification = notificationOpt.get();
			notification.setStatus(Notification.Status.READ);
			return notificationRepository.save(notification);
			}
		return null;
	}


	public void deleteNotification(Integer notificationId) {
		notificationRepository.deleteById(notificationId);
	}

	private void sendEmailNotification(String to, String subject, String message, byte[] pdf) throws MessagingException {
		emailService.sendEmailWithAttachment(to, subject, message, pdf, "notification.pdf");
	}


	public void notifyAppointment(Appointment appointment) throws DocumentException, IOException, MessagingException {
		String message = "You have an appointment with Dr. " + appointment.getDoctor().getUser().getName() +" on " + appointment.getAppointmentDate() + ". Reason: " + appointment.getReason();createNotification(appointment.getPatient().getUser(), message);
		}


	public void notifyDoctorAvailability(DoctorAvailability availability) throws DocumentException, IOException, MessagingException {
		String message = "Dr. " + availability.getDoctor().getUser().getName() + " is available on " +availability.getAvailableDate() + " from " + availability.getStartTime() + " to " + availability.getEndTime();createNotification(availability.getDoctor().getUser(), message);
	}
	
}
