package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateAdminRequest {

	@NotEmpty(message="{email.notNull}")
	@Email(message="{email.invalidFormat}")
	private String email;
	
	@NotEmpty(message="{username.notNull}")
	private String username;
	
	@NotEmpty(message="{password.notNull}")
	private String password;
	
}
