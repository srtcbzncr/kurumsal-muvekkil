package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.RoleAlreadyExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.RoleNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class RoleExceptionHandler {

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<Object> roleNotFoundExceptionHandler(RoleNotFoundException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.NOT_FOUND, response);
	}
	
	@ExceptionHandler(RoleAlreadyExistsException.class)
	public ResponseEntity<Object> roleNotFoundExceptionHandler(RoleAlreadyExistsException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
}
