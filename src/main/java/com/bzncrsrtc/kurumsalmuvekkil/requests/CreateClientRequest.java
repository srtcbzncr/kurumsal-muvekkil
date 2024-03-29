package com.bzncrsrtc.kurumsalmuvekkil.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientRequest {

	@NotEmpty(message="{identificationNumber.notNull}")
	@Size(min=11, max=11, message="{identificationNumber.sizeError}")
	private String identificationNumber;
	
	@NotEmpty(message="{firstName.notNull}")
	private String firstName;
	
	@NotEmpty(message="{lastName.notNull}")
	private String lastName;
	
	@NotEmpty(message="{phone.notNull}")
	@Max(value=15, message="{phone.sizeError}")
	private String phone;
	
	@Valid
	private CreateAdminRequest user;
	
}
