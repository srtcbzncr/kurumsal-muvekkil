package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {

	@NotNull(message="{id.notNull}")
	private UUID id;
	
	@NotEmpty(message="{email.notNull}")
	@Email(message="{email.invalidFormat}")
	private String email;
	
	@NotEmpty(message="{username.notNull}")
	private String username;
	
	@NotEmpty(message="{password.notNull}")
	private String password;
	
	@NotEmpty(message="{role.notNull}")
	private UUID role_id;
	
}
