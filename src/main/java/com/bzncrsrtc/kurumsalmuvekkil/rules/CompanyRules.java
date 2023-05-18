package com.bzncrsrtc.kurumsalmuvekkil.rules;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ThereIsNoCompanyException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@Component
public class CompanyRules {
	
	private final CompanyRepository companyRepository;
	private final MessageSource messageSource;
	
	public CompanyRules(CompanyRepository companyRepository, MessageSource messageSource) {
		this.companyRepository = companyRepository;
		this.messageSource = messageSource;
	}

	public void isNull(Optional<Company> company, Locale locale) {
		if(company.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
	}
	
	public void isNull(Company company, Locale locale) {
		if(company == null) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
	}
	
	public void isNull(List<Company> companies, Locale locale) {
		if(companies.size() == 0) {
			throw new ThereIsNoCompanyException(messageSource.getMessage("there.is.no.company.message", null, locale));
		}
	}

	public void isThere(UUID id, Locale locale) {
		if(id == null || companyRepository.existsById(id)) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
	}
	
	public void isDeleted(Company company, Locale locale) {
		if(company.isDeleted()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
	}
	
	public void isPassive(Company company, Locale locale) {
		if(!company.isActive()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
	}
	
}
