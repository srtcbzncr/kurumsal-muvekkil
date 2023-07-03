package com.bzncrsrtc.kurumsalmuvekkil.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

	@NotEmpty(message="{username.notNull}")
	private String username;
	
	@NotEmpty(message="{password.notNull}")
	private String password;
	
}
