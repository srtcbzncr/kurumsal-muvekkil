package com.bzncrsrtc.kurumsalmuvekkil.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ThereIsNoCompanyException;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;

@ControllerAdvice
public class CompanyExceptionHandler {
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<ErrorResponse> companyNotFoundExceptionHandler(CompanyNotFoundException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ThereIsNoCompanyException.class)
	public ResponseEntity<ErrorResponse> thereIsNoCompanyExceptionHandler(ThereIsNoCompanyException exception){
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
}
