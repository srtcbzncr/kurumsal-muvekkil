package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<Object> missingRequestHeaderExceptionHandler(MissingRequestHeaderException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.BAD_REQUEST, response);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
}
