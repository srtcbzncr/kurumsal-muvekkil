package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.CompanyMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.responses.AllCompaniesResponse;
import com.bzncrsrtc.kurumsalmuvekkil.services.concretes.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	private final CompanyService companyService;
	private final CompanyMapper companyMapper;
	
	public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}

	@GetMapping("")
	public ResponseEntity<List<AllCompaniesResponse>> findAll(){
		List<Company> companies = companyService.findAll();
		List<AllCompaniesResponse> companiesResponse = companyMapper.getAllCompaniesResponse(companies);
		return ResponseEntity.ok(companiesResponse);
	}
	
}
