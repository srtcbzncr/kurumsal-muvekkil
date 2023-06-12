package com.bzncrsrtc.kurumsalmuvekkil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT)
public class AlreadyHasSubscriptionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public AlreadyHasSubscriptionException(String message) {
		super(message);
	}
	
}
