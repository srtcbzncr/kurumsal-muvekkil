package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.EmailAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.IdentificationNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PhoneNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UsernameAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ValidationErrorResponse;

@ControllerAdvice
public class GeneralExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<Object> missingRequestHeaderExceptionHandler(MissingRequestHeaderException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.BAD_REQUEST, response);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    List<ValidationErrorResponse> errors = new ArrayList<ValidationErrorResponse>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	    	ValidationErrorResponse validationError = new ValidationErrorResponse(((FieldError) error).getField(), error.getDefaultMessage());
	        errors.add(validationError);
	    });
	    return ResponseHandler.generateValidationErrorResponse(null, HttpStatus.BAD_REQUEST, errors);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(IdentificationNumberAlreadyUsedException.class)
	public ResponseEntity<Object> handleIdentificationNumberAlreadyUsedException(IdentificationNumberAlreadyUsedException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(PhoneNumberAlreadyUsedException.class)
	public ResponseEntity<Object> handlePhoneNumberAlreadyUsedException(PhoneNumberAlreadyUsedException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(UsernameAlreadyUsedException.class)
	public ResponseEntity<Object> handleUsernameAlreadyUsedException(UsernameAlreadyUsedException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(EmailAlreadyUsedException.class)
	public ResponseEntity<Object> handleEmailAlreadyUsedException(EmailAlreadyUsedException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
}
