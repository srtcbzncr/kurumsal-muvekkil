package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.CompanyMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.AllCompaniesResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CreateCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFilesOfCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetLawyersOfCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionOfCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UpdateCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.services.concretes.CompanyService;

import jakarta.validation.Valid;

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
		List<AllCompaniesResponse> companiesResponse = companyMapper.getAllCompaniesResponseFromCompanyModel(companies);
		return ResponseEntity.ok(companiesResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GetCompanyResponse> findById(@PathVariable UUID id){
		Company company = companyService.findById(id);
		GetCompanyResponse response = companyMapper.getCompanyResponseFromCompanyModel(company);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("")
	public ResponseEntity<CreateCompanyResponse> create(@Valid @RequestBody CreateCompanyRequest createCompanyRequest){
		Company company = companyMapper.getCompanyModelFromCreateCompanyRequest(createCompanyRequest);
		Company savedCompany = companyService.create(company);
		CreateCompanyResponse response = companyMapper.getCreateCompanyResponseFromCompanyModel(savedCompany);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("")
	public ResponseEntity<UpdateCompanyResponse> update(@Valid @RequestBody UpdateCompanyRequest updateCompanyRequest){
		Company company = companyMapper.getCompanyModelFromUpdateCompanyRequest(updateCompanyRequest);
		Company savedCompany = companyService.update(company);
		UpdateCompanyResponse response = companyMapper.getUpdateCompanyResponseFromCompanyModel(savedCompany);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id){
		companyService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/subscription")
	public ResponseEntity<GetSubscriptionOfCompanyResponse> getSubscription(@PathVariable UUID id){
		Subscription subscription = companyService.getSubscription(id);
		GetSubscriptionOfCompanyResponse response = companyMapper.getSubscriptionOfCompanyResponseFromSubscriptionModel(subscription);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/lawyers")
	public ResponseEntity<List<GetLawyersOfCompanyResponse>> getLawyers(@PathVariable UUID id){
		List<Lawyer> lawyers = companyService.getLawyers(id);
		List<GetLawyersOfCompanyResponse> response = companyMapper.getLawyersOfCompanyResponseFromLawyersList(lawyers);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/files")
	public ResponseEntity<List<GetFilesOfCompanyResponse>> getFiles(@PathVariable UUID id){
		List<File> files = companyService.getFiles(id);
		List<GetFilesOfCompanyResponse> response = companyMapper.getFilesOfCompanyResponseFromFilesList(files);
		return ResponseEntity.ok(response);
	}
	
}
