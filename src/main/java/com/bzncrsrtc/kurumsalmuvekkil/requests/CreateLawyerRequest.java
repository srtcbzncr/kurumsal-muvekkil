package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLawyerRequest {
	
	@NotNull(message="{company.id.notNull}")
	private UUID companyId;
	
	@Valid
	private CreateAdminRequest user;
	
	@NotEmpty(message="identification.number.notNull}")
	private String identificationNumber;
	
	@NotEmpty(message="firstName.notNull}")
	private String firstName;
	
	@NotEmpty(message="lastName.notNull}")
	private String lastName;
	
	@NotEmpty(message="phone.notNull}")
	@Max(value=15, message="{phone.sizeError}")
	private String phone;
	
}
