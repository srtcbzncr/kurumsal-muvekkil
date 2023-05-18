package com.bzncrsrtc.kurumsalmuvekkil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ThereIsNoFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ThereIsNoFileException(String message) {
		super(message);
	}
	
}
