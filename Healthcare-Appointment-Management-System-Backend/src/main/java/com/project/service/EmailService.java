package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;




//import com.project.repository.NotificationRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentFilename) throws MessagingException {
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(text);
	    helper.addAttachment(attachmentFilename, new ByteArrayResource(attachment));
	    mailSender.send(message);
		}

}

	
//	public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment,String attachmentFilename) throws MessagingException {
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, true);
//		helper.setTo(to);
//	    helper.setSubject(subject);
//	    helper.setText(text);
//	    helper.addAttachment(attachmentFilename, new ByteArrayResource(attachment));
//	    mailSender.send(message);
//	    }
	
//	public void forgotMail(String to,String subject,String password) throws MessagingException{
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper=new MimeMessageHelper(message,true);
//        helper.setTo(to);
//        helper.setSubject(subject);
// 
//        String htmlMsg = "<p><b>Your login details Billing System</b><br><b>Email: </b>" + to + "<br><b> Password: </b>" +
//        				password + "http://localhost:8000/\"> Click here to login </a></p>";
// 
//        message.setContent(htmlMsg,"text/html");
//        mailSender.send(message);
//	
//	public void sendPasswordResetEmail(String to, String resetToken) throws MessagingException {
//        String subject = "Password Reset Request";
//        String text = "Dear User,\n\nWe received a request to reset your password. Please use the following token to reset your password:\n\n" +
//                      "Reset Token: " + resetToken + "\n\n" +
//                      "If you did not request a password reset, please ignore this email.\n\n" +
//                      "Best regards,\nYour Team";
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(text);
//
//        mailSender.send(message);
//    }
//}

