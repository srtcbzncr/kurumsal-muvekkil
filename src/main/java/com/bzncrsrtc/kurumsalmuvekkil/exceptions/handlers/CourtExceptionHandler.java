package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtCanNotDeleteException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class CourtExceptionHandler {

	@ExceptionHandler(CourtNotFoundException.class)
	public ResponseEntity<Object> courtNotFoundExceptionHandler(CourtNotFoundException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.NOT_FOUND, response);
	}
	
	@ExceptionHandler(CourtExistsException.class)
	public ResponseEntity<Object> courtExistsExceptionHandler(CourtExistsException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
	@ExceptionHandler(CourtCanNotDeleteException.class)
	public ResponseEntity<Object> courtCanNotDeleteHandler(CourtCanNotDeleteException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
}
