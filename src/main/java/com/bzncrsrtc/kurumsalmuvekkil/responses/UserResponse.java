package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class UserResponse {

	private UUID id;
	private String email;
	private String username;
	private RoleResponse role;
	
}
