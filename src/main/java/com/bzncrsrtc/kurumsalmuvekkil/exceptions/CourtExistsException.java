package com.bzncrsrtc.kurumsalmuvekkil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT)
public class CourtExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CourtExistsException(String message) {
		super(message);
	}

}
