package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PlanNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class PlanExceptionHandler {

	@ExceptionHandler(PlanNotFoundException.class)
	public ResponseEntity<Object> handlePlanNotFoundException(PlanNotFoundException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(response, HttpStatus.NOT_FOUND, null);
	}
	
}
