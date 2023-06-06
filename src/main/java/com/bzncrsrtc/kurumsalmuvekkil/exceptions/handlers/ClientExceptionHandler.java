package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ClientNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class ClientExceptionHandler {

	@ExceptionHandler(ClientNotFoundException.class)
	public ResponseEntity<Object> clientNotFoundExceptionHandler(ClientNotFoundException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.NOT_FOUND, response);
	}
	
}
