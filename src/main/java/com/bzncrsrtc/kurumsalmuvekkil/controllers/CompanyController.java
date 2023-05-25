package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFileResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetLawyerResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.services.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	private final CompanyService companyService;
	private final ResponseMapper responseMapper;
	private final RequestMapper requestMapper;
	
	public CompanyController(CompanyService companyService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.companyService = companyService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}

	@GetMapping("")
	public ResponseEntity<List<GetCompanyResponse>> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		List<Company> companies = companyService.findAll(locale);
		List<GetCompanyResponse> response = responseMapper.getCompanyListResponse(companies);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GetCompanyResponse> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		Company company = companyService.findById(id, locale);
		GetCompanyResponse response = responseMapper.getCompanyResponse(company);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("")
	public ResponseEntity<GetCompanyResponse> create(@Valid @RequestBody CreateCompanyRequest createCompanyRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		Company company = requestMapper.fromCreateCompanyRequest(createCompanyRequest);
		Company savedCompany = companyService.create(company, locale);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCompany.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateCompanyRequest updateCompanyRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		Company company = requestMapper.fromUpdateCompanyRequest(updateCompanyRequest);
		companyService.update(company, locale);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		companyService.delete(id, locale);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/subscription")
	public ResponseEntity<GetSubscriptionResponse> getSubscription(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		Subscription subscription = companyService.getSubscription(id, locale);
		GetSubscriptionResponse response = responseMapper.getSubscriptionResponse(subscription);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/lawyers")
	public ResponseEntity<List<GetLawyerResponse>> getLawyers(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		List<Lawyer> lawyers = companyService.getLawyers(id, locale);
		List<GetLawyerResponse> response = responseMapper.getLawyerListResponse(lawyers);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/files")
	public ResponseEntity<List<GetFileResponse>> getFiles(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = localeStr.equals("en") ? new Locale("en") : new Locale("tr");
		
		List<File> files = companyService.getFiles(id, locale);
		List<GetFileResponse> response = responseMapper.getFileListResponse(files);
		
		return ResponseEntity.ok(response);
	}
	
}
