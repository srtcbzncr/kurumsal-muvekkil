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
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateSubscriptionRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateSubscriptionRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.SubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.SubscriptionStatisticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.SubscriptionService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

	private SubscriptionService subscriptionService;
	private ResponseMapper responseMapper;
	private RequestMapper requestMapper;
	
	public SubscriptionController(SubscriptionService subscriptionService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.subscriptionService = subscriptionService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> stats(@RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCount = subscriptionService.allCount(locale);
		int activeCount = subscriptionService.activeCount(locale);
		int passiveCount = subscriptionService.passiveCount(locale);
		int deletedCount = subscriptionService.deletedCount(locale);
		
		SubscriptionStatisticsResponse response = new SubscriptionStatisticsResponse(allCount, activeCount, passiveCount, deletedCount);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Subscription> subscriptions = subscriptionService.findAll(locale);
		List<SubscriptionResponse> response = responseMapper.getSubscriptionListResponse(subscriptions);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/active")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllActive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Subscription> subscriptions = subscriptionService.findAllActive(locale);
		List<SubscriptionResponse> response = responseMapper.getSubscriptionListResponse(subscriptions);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllPassive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Subscription> subscriptions = subscriptionService.findAllPassive(locale);
		List<SubscriptionResponse> response = responseMapper.getSubscriptionListResponse(subscriptions);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllDeleted(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Subscription> subscriptions = subscriptionService.findAllDeleted(locale);
		List<SubscriptionResponse> response = responseMapper.getSubscriptionListResponse(subscriptions);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Subscription subscription = subscriptionService.findById(id, locale);
		SubscriptionResponse response = responseMapper.getSubscriptionResponse(subscription);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> create(@Valid @RequestBody CreateSubscriptionRequest createSubscriptionRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Subscription subscription = requestMapper.fromCreateSubscriptionRequestToSubscription(createSubscriptionRequest);
		Subscription savedSubscription = subscriptionService.create(subscription, locale);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedSubscription.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("/{id}/setActive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setActive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Subscription updatedSubscription = subscriptionService.setActive(id, locale);
		SubscriptionResponse response = responseMapper.getSubscriptionResponse(updatedSubscription);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
		
	}
	
	@PutMapping("/{id}/setPassive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setPassive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Subscription updatedSubscription = subscriptionService.setPassive(id, locale);
		SubscriptionResponse response = responseMapper.getSubscriptionResponse(updatedSubscription);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
		
	}
	
	@PutMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateSubscriptionRequest updateSubscriptionRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Subscription subscription = requestMapper.fromUpdateSubscriptionRequestToSubscription(updateSubscriptionRequest);
		
		subscriptionService.update(subscription, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		subscriptionService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
}
