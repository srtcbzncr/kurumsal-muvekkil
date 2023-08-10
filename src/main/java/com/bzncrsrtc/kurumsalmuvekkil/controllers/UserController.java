package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserStatisticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	private RequestMapper requestMapper;
	private ResponseMapper responseMapper;
	
	public UserController(UserService userService, RequestMapper requestMapper, ResponseMapper responseMapper) {
		this.userService = userService;
		this.requestMapper = requestMapper;
		this.responseMapper = responseMapper;
	}
	
	@GetMapping("/admins/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> stats(@RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCount = userService.allAdminsCount(locale);
		int activeCount = userService.activeAdminsCount(locale);
		int passiveCount = userService.passiveAdminsCount(locale);
		int deletedCount = userService.deletedAdminsCount(locale);
		
		UserStatisticsResponse response = new UserStatisticsResponse(allCount, activeCount, passiveCount, deletedCount);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	
	@GetMapping("/admins/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllAdmins(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<User> admins = userService.findAllAdmins(locale);
		List<UserResponse> response = responseMapper.getUserListResponse(admins);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/admins/active")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findActiveAdmins(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<User> admins = userService.findActiveAdmins(locale);
		List<UserResponse> response = responseMapper.getUserListResponse(admins);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/admins/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findPassiveAdmins(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<User> admins = userService.findPassiveAdmins(locale);
		List<UserResponse> response = responseMapper.getUserListResponse(admins);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/admins/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findDeletedAdmins(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<User> admins = userService.findDeletedAdmins(locale);
		List<UserResponse> response = responseMapper.getUserListResponse(admins);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}

}
