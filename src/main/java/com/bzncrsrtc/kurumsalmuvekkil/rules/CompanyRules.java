package com.bzncrsrtc.kurumsalmuvekkil.rules;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@Component
public class CompanyRules {
	
	private final CompanyRepository companyRepository;
	
	public CompanyRules(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public void isNull(Optional<Company> company) {
		if(company.isEmpty()) {
			throw new CompanyNotFoundException("message");
		}
	}
	
	public void isNull(Company company) {
		if(company == null) {
			throw new CompanyNotFoundException("message");
		}
	}

	public void isThere(UUID id) {
		if(id == null || companyRepository.existsById(id)) {
			throw new CompanyNotFoundException("message");
		}
	}
	
	public void isDeleted(Company company) {
		if(company.isDeleted()) {
			throw new CompanyNotFoundException("message");
		}
	}
	
	public void isPassive(Company company) {
		if(!company.isActive()) {
			throw new CompanyNotFoundException("message");
		}
	}
	
}
