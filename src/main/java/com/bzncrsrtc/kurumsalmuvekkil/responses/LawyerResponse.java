package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class LawyerResponse {

	private UUID id;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	
}