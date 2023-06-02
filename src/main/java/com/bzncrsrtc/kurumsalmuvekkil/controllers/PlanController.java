package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionWithoutPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.PlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/plan")
public class PlanController {
	
	private final PlanService planService;
	private final ResponseMapper responseMapper;
	private final RequestMapper requestMapper;
	
	public PlanController(PlanService planService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.planService = planService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Plan> plans = planService.findAll(locale);
		List<GetPlanResponse> response = responseMapper.getPlanListResponse(plans);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Plan plan = planService.findById(id, locale);
		GetPlanResponse response = responseMapper.getPlanResponse(plan);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	public ResponseEntity<Object> create(@Valid @RequestBody CreatePlanRequest createPlanRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Plan plan = requestMapper.fromCreatePlanRequestToPlan(createPlanRequest);
		Plan savedPlan = planService.create(plan, locale);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPlan.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdatePlanRequest updatePlanRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Plan plan = requestMapper.fromUpdatePlanRequestToPlan(updatePlanRequest);
		planService.update(plan, locale);
		
		return ResponseHandler.generateResponse(plan, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept_Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		planService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/subscriptions")
	public ResponseEntity<Object> getSubscriptions(@PathVariable UUID id, @RequestHeader(name = "Accept_Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Subscription> subscriptions = planService.getSubscriptions(id, locale);
		List<GetSubscriptionWithoutPlanResponse> response = responseMapper.getSubscriptionWithoutPlanListResponse(subscriptions);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}

}
