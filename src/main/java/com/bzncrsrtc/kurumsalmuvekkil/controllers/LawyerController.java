package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateLawyerRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateLawyerRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.LawyerResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.LawyerStatisticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.LawyerService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/lawyer")
public class LawyerController {

	private LawyerService lawyerService;
	private ResponseMapper responseMapper;
	private RequestMapper requestMapper;
	
	public LawyerController(LawyerService lawyerService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.lawyerService = lawyerService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> stats(@RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCount = lawyerService.allCount(locale);
		int activeCount = lawyerService.activeCount(locale);
		int passiveCount = lawyerService.passiveCount(locale);
		int deletedCount = lawyerService.deletedCount(locale);
		
		LawyerStatisticsResponse response = new LawyerStatisticsResponse(allCount, activeCount, passiveCount, deletedCount);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Lawyer> lawyers = lawyerService.findAll(locale);
		List<LawyerResponse> response = responseMapper.getLawyerListResponse(lawyers);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/active")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllActive(@RequestHeader(name="Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Lawyer> lawyers = lawyerService.findAllActive(locale);
		List<LawyerResponse> response = responseMapper.getLawyerListResponse(lawyers);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllPassive(@RequestHeader(name="Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Lawyer> lawyers = lawyerService.findAllPassive(locale);
		List<LawyerResponse> response = responseMapper.getLawyerListResponse(lawyers);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllDeleted(@RequestHeader(name="Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Lawyer> lawyers = lawyerService.findAllDeleted(locale);
		List<LawyerResponse> response = responseMapper.getLawyerListResponse(lawyers);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Lawyer lawyer = lawyerService.findById(id, locale);
		LawyerResponse response = responseMapper.getLawyerResponse(lawyer);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	public ResponseEntity<Object> create(@Valid @RequestBody CreateLawyerRequest createLawyerRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Lawyer lawyer = requestMapper.fromCreateLawyerRequestToLawyer(createLawyerRequest);
		
		Lawyer savedLawyer = lawyerService.create(lawyer, locale);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedLawyer.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateLawyerRequest updateLawyerRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Lawyer lawyer = requestMapper.fromUpdateLawyerRequestToLawyer(updateLawyerRequest);
		lawyerService.update(lawyer, locale);
		
		return ResponseHandler.generateResponse(lawyer, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		lawyerService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/user")
	public ResponseEntity<Object> getUser(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		User user = lawyerService.getUser(id, locale);
		UserResponse response = responseMapper.getUserResponse(user);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
}
