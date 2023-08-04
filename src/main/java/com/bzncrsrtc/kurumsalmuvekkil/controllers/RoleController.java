package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Role;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateRoleRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateRoleRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.RoleResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.RoleService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("role")
public class RoleController {
	
	private RoleService roleService;
	private ResponseMapper responseMapper;
	private RequestMapper requestMapper;
	
	public RoleController(RoleService roleService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.roleService = roleService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Role> roles = roleService.findAll(locale);
		List<RoleResponse> response = responseMapper.getRoleListResponse(roles);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Role role = roleService.findById(id, locale);
		RoleResponse response = responseMapper.getRoleResponse(role);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	public ResponseEntity<Object> create(@Valid @RequestBody CreateRoleRequest createRoleRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Role role = requestMapper.fromCreateRoleRequestToRole(createRoleRequest);
		Role savedRole = roleService.create(role, locale);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRole.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateRoleRequest updateRoleRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Role role = requestMapper.fromUpdateRoleRequestToRole(updateRoleRequest);
		roleService.update(role, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		roleService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}

}
