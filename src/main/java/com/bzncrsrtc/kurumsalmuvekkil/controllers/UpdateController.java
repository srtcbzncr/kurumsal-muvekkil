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
import com.bzncrsrtc.kurumsalmuvekkil.models.Update;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateUpdateRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateUpdateRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UpdateResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.UpdateService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/update")
public class UpdateController {

	private UpdateService updateService;
	private ResponseMapper responseMapper;
	private RequestMapper requestMapper;
	
	public UpdateController(UpdateService updateService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.updateService = updateService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Update> updates = updateService.findAll(locale);
		List<UpdateResponse> response = responseMapper.getUpdateListResponse(updates);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Update update = updateService.findById(id, locale);
		UpdateResponse response = responseMapper.getUpdateResponse(update);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	public ResponseEntity<Object> create(@Valid @RequestBody CreateUpdateRequest createUpdateRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Update update = requestMapper.fromCreateUpdateRequestToUpdate(createUpdateRequest);
		Update savedUpdate = updateService.create(update, locale);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUpdate.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateUpdateRequest updateUpdateRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Update update = requestMapper.fromUpdateUpdateRequestToUpdate(updateUpdateRequest);
		
		updateService.update(update, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		updateService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
}
