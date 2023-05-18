package com.bzncrsrtc.kurumsalmuvekkil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ThereIsNoCompanyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ThereIsNoCompanyException(String message) {
		super(message);
	}

}
