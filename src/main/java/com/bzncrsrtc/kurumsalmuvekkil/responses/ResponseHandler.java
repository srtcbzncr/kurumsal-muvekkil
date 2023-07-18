package com.bzncrsrtc.kurumsalmuvekkil.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

	public static ResponseEntity<Object> generateResponse(Object object, HttpStatus status, ErrorResponse error){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", object);
		map.put("status", status.value());
		map.put("error", error);
		
		return new ResponseEntity<Object>(map, status);
	}
	
	public static ResponseEntity<Object> generateValidationErrorResponse(Object object, HttpStatus status, ValidationErrorResponse error){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", object);
		map.put("status", status.value());
		map.put("error", error);
		
		return new ResponseEntity<Object>(map, status);
	}
	
}
