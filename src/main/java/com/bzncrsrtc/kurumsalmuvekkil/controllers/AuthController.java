package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bzncrsrtc.kurumsalmuvekkil.requests.LoginRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.LoginResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.AuthService;
import com.bzncrsrtc.kurumsalmuvekkil.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest, @RequestHeader(name="Accept-Language", required=false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Authentication authentication = authService.login(loginRequest.getUsername(), loginRequest.getPassword(), locale);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		
		return ResponseHandler.generateResponse(new LoginResponse(userDetails.getUsername()), HttpStatus.OK, null);
	}
	
}
