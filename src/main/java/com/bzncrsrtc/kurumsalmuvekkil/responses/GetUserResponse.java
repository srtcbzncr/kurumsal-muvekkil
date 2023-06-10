package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class GetUserResponse {

	private UUID id;
	private String email;
	private String username;
	private String role;
	
}
