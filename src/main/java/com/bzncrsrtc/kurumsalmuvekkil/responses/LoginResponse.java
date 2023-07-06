package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String username;
	private String password;
	private String role;
	
	public LoginResponse(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
}
