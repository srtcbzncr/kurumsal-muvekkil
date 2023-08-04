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
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CompanyStatisticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.CompanyService;

import jakarta.validation.Valid;

@CrossOrigin
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
	
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> stats(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCount = companyService.allCount(locale);
		int activeCount = companyService.activeCount(locale);
		int passiveCount = companyService.passiveCount(locale);
		int deletedCount = companyService.deletedCount(locale);
		
		CompanyStatisticsResponse response = new CompanyStatisticsResponse(allCount, activeCount, passiveCount, deletedCount);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Company> companies = companyService.findAll(locale);
		List<CompanyResponse> response = responseMapper.getCompanyListResponse(companies);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/active")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllActive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Company> companies = companyService.findAllActive(locale);
		List<CompanyResponse> response = responseMapper.getCompanyListResponse(companies);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllPassive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Company> companies = companyService.findAllPassive(locale);
		List<CompanyResponse> response = responseMapper.getCompanyListResponse(companies);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllDeleted(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Company> companies = companyService.findAllDeleted(locale);
		List<CompanyResponse> response = responseMapper.getCompanyListResponse(companies);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_LAWYER')")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Company company = companyService.findById(id, locale);
		CompanyResponse response = responseMapper.getCompanyResponse(company);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> create(@Valid @RequestBody CreateCompanyRequest createCompanyRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Company company = requestMapper.fromCreateCompanyRequestToCompany(createCompanyRequest);
		Company savedCompany = companyService.create(company, locale);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCompany.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateCompanyRequest updateCompanyRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Company company = requestMapper.fromUpdateCompanyRequestToCompany(updateCompanyRequest);
		Company savedCompany = companyService.update(company, locale);
		CompanyResponse response = responseMapper.getCompanyResponse(savedCompany);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PutMapping("/{id}/setActive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setActive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Company company = companyService.setActive(id, locale);
		CompanyResponse response = responseMapper.getCompanyResponse(company);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PutMapping("/{id}/setPassive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setPassive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Company company = companyService.setPassive(id, locale);
		CompanyResponse response = responseMapper.getCompanyResponse(company);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		companyService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
}
