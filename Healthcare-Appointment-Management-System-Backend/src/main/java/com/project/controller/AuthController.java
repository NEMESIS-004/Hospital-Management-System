package com.project.controller;

import java.util.Map;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;
import java.util.Collections;
import java.util.HashMap;

import com.project.constants.ErrorConstants;
import com.project.exception.CustomException;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import com.project.utils.JwtUtil;
 
import jakarta.servlet.http.HttpServletRequest;
 
 
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/authController")
public class AuthController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authManager;
   
    
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
 
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerHandler(@RequestBody User user) {
    	logger.info("Received registration request for user: {}", user.getEmail());
        try {
            userService.registerUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            logger.info("User registered successfully: {}", user.getEmail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (CustomException e) {
     	    logger.error("Duplicate entry detected for user email: {}", user.getEmail(), e);
            throw e;
        }
        catch (Exception e) {
        	logger.error("Unexpected error occurred while registering user", e);
            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginHandler(@RequestBody User body) {
		logger.info("Received login request for user: {}", body.getEmail());
		try {
	        if (body.getEmail() == null || body.getPassword() == null) {
	        	logger.warn("Login request with missing email or password");
	        	throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
	        }
 
	        UsernamePasswordAuthenticationToken authInputToken =
	                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
	        authManager.authenticate(authInputToken);
 
	        String token = jwtUtil.generateToken(body.getEmail());
	        logger.info("User {} logged in successfully. JWT token generated.", body.getEmail());
	        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
	    } catch (AuthenticationException e) {
	    	logger.warn("Authentication failed for user: {}", body.getEmail(), e);
            throw e;
	    } catch (Exception e) {
	    	logger.error("Unexpected error during login for user: {}", body.getEmail(), e);
	    	throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
//    @PostMapping("/forgot-password")
//    public Map<String, Object> forgotPasswordHandler(@RequestBody Map<String, String> body) {
//    	String email = body.get("email");
//    	LoggerUtil.authLogger.info("Processing forgot password request for email: {}", email);
//    	Optional<User> user = userRepo.findByEmail(email);
//    	if (user == null) {
//    		LoggerUtil.authLogger.warn(LoggerUtil.WARN_NOT_FOUND, "User", email);
//    		return Collections.singletonMap("message", "User not found");
//    		}
//    	// Generate a reset token and send email (implementation not shown)
//        String resetToken = jwtUtil.generateToken(email);
//        LoggerUtil.authLogger.info("Password reset token generated for email: {}", email);
//        return Collections.singletonMap("reset-token", resetToken);
//        }
//    
    
	@PostMapping("/user")
	public ResponseEntity<?> getUserData(@RequestBody User user) {
		logger.info("Received request to get user data for email: {}", user.getEmail());
	    try {
	        Optional<User> existingUserOptional = userRepo.findByEmail(user.getEmail());
	        if (!existingUserOptional.isPresent()) {
	        	logger.warn("User not found for email: {}", user.getEmail());
	            throw new CustomException(ErrorConstants.INVALID_DATA, HttpStatus.NOT_FOUND);
	        }

	        User existingUser=existingUserOptional.get();
	        logger.info("Retrieved user data for email: {}", user.getEmail());
	        return new ResponseEntity<>(existingUser, HttpStatus.OK);
	    }catch(CustomException e) {
	    	logger.error("Custom exception while getting user data for email: {}", user.getEmail(), e);
            throw e;
	    } catch (Exception e) {
	    	logger.error("Unexpected error while getting user data for email: {}", user.getEmail(), e);
	        throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
 
	@PatchMapping("/update-user")
	public ResponseEntity<Map<String, String>> updateUserHandler(@RequestBody User user, HttpServletRequest request) {
		logger.info("Received update request for user: {}", user.getEmail());
	    try {
	        userService.updateUser(user);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "User updated successfully");
	        logger.info("User {} updated successfully.", user.getEmail());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (CustomException e) {
	    	logger.error("User not found during update for email: {}", user.getEmail(), e);
            throw e;
	    } catch (Exception e) {
	    	logger.error("Unexpected error during user update for email: {}", user.getEmail(), e);
	        throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@DeleteMapping("/delete-user")
	public ResponseEntity<Map<String, String>> deleteUserHandler(@RequestBody Map<String, String> body, HttpServletRequest request) {
		String email = body.get("email");
		logger.info("Received delete request for user with email: {}", email);
	    try {
	        userService.deleteUser(email);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "User deleted successfully");
	        logger.info("User with email {} deleted successfully.", email);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (CustomException e) {
	    	logger.error("User not found during deletion for email: {}", email, e);
            throw e;
	    } catch (Exception e) {
	    	logger.error("Unexpected error during user deletion for email: {}", email, e);
	        throw new CustomException(ErrorConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
 
 
 
}

