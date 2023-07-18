package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {

	private String type;
	private List<ValidationFieldError> errors;
	
}
