package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateLawyerRequest {

	private UUID companyId;
	private CreateUserRequest user;
	private String title;
	private String identificationNumber;
	private String firstName;
	private String lastName;
	private String phone;
	
}
