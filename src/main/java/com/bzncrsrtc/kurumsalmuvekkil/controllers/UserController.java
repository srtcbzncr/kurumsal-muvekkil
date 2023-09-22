package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateAdminRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserStatisticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.services.UserService;

import jakarta.validation.Valid;


@CrossOrigin
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
	
	@PutMapping("/{id}/setActive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setActive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		User updatedUser = userService.setActive(id, locale);
		UserResponse response = responseMapper.getUserResponse(updatedUser);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PutMapping("/{id}/setPassive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setPassive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		User updatedUser = userService.setPassive(id, locale);
		UserResponse response = responseMapper.getUserResponse(updatedUser);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		userService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> createAdmin (@Valid @RequestBody CreateAdminRequest createAdminRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		User user = requestMapper.fromCreateUserRequestToUser(createAdminRequest);
		User savedUser = userService.createAdmin(user, locale);
		UserResponse response = responseMapper.getUserResponse(savedUser);
		
		return ResponseHandler.generateResponse(response, HttpStatus.CREATED, null);
	}

}
