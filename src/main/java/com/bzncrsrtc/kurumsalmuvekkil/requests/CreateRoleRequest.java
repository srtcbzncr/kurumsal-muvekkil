package com.bzncrsrtc.kurumsalmuvekkil.requests;

import jakarta.validation.constraints.NotEmpty;

public class CreateRoleRequest {

	@NotEmpty(message="{name.notNull}")
	private String name;
	
}
