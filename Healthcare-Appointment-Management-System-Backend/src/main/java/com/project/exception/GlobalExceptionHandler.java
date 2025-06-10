package com.project.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  
@ControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        errorResponse.setStatus(ex.getStatus().value());
        errorResponse.setError(ex.getStatus().getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false).substring(4)); // Remove prefix
        return new ResponseEntity<>(errorResponse, ex.getStatus());
	}
}
