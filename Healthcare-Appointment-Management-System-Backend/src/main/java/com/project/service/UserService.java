package com.project.service;
 

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.User;
import com.project.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
 
 
@Slf4j
@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

 
    public User registerUser(User user) throws CustomException {
    	Optional<User> opt = userRepository.findByEmail(user.getEmail());
    	if (opt.isPresent()) {
    		throw new CustomException(ErrorConstants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
    	}
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	userRepository.save(user);
    	return user;
    }
 
//    public String forgotPassword(String email) {
//    	Optional<User> userOptional = userRepository.findByEmail(email);
//    	if (userOptional.isPresent()) {
//    		String resetToken = jwtutil.generateToken(email);
//    		try {
//    			emailservice.sendPasswordResetEmail(email, resetToken);
//			} catch (MessagingException e) {
//				throw new ValidationException("Failed to send password reset email");
//			}
//    		return resetToken;
//		}else {
//			throw new ValidationException("Email not found");}}
 
 
   
    public User updateUser(User user) {
        if (user.getEmail() == null || user.getName() == null || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        Optional<User> existingUserOptional = userRepository.findByEmail(user.getEmail());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(existingUser);
            return existingUser;
        } else {
            throw new CustomException(ErrorConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
 
 
   
    public void deleteUser(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new CustomException(ErrorConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
 
}

