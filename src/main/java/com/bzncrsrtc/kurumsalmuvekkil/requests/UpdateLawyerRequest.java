package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateLawyerRequest {

	private UUID id;
	private UUID companyId;
	private UpdateUserRequest user;
	private String title;
	private String identificationNumber;
	private String firstName;
	private String lastName;
	private String phone;
	
	
}
