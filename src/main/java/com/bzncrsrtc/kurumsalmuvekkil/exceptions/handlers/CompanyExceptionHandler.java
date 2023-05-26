package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;

@ControllerAdvice
public class CompanyExceptionHandler {
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<Object> companyNotFoundExceptionHandler(CompanyNotFoundException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.NOT_FOUND.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.NOT_FOUND, response);
	}
	
	@ExceptionHandler(CompanyExistsException.class)
	public ResponseEntity<Object> companyExistsExceptionHandler(CompanyExistsException exception){
		ErrorResponse response = new ErrorResponse( HttpStatus.CONFLICT.name(), exception.getMessage());
		
		return ResponseHandler.generateResponse(null, HttpStatus.CONFLICT, response);
	}
	
}
