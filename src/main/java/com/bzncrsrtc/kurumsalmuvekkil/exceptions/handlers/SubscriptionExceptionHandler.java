package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.AlreadyHasSubscriptionException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.SubscriptionNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class SubscriptionExceptionHandler {
	
	@ExceptionHandler(SubscriptionNotFoundException.class)
	public ResponseEntity<Object> subscriptionNotFoundExceptionHandler(SubscriptionNotFoundException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.NOT_FOUND, response);
	}
	
	@ExceptionHandler(AlreadyHasSubscriptionException.class)
	public ResponseEntity<Object> alreadyHasSubscriptionExceptionHandler(AlreadyHasSubscriptionException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.NOT_FOUND, response);
	}
}
