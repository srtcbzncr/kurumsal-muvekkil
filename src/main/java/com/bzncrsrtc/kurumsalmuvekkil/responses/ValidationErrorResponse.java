package com.bzncrsrtc.kurumsalmuvekkil.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationErrorResponse {

	String field;
	String message;
	
}
