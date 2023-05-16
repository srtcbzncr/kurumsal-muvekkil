package com.bzncrsrtc.kurumsalmuvekkil.rules;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@Component
public class CompanyRules {
	
	private final MessageSource messageSource;
	private final CompanyRepository companyRepository;
	
	public CompanyRules(MessageSource messageSource, CompanyRepository companyRepository) {
		this.messageSource = messageSource;
		this.companyRepository = companyRepository;
	}

	public void isNull(Optional<Company> company) {
		if(company.isEmpty()) {
			Locale locale = LocaleContextHolder.getLocale();
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, "An error occured", locale));
		}
	}
	
	public void isNull(Company company) {
		if(company == null) {
			Locale locale = LocaleContextHolder.getLocale();
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, "An error occured", locale));
		}
	}

	public void isThere(UUID id) {
		if(id == null || companyRepository.existsById(id)) {
			Locale locale = LocaleContextHolder.getLocale();
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, "An error occured", locale));
		}
	}
	
	public void isDeleted(Company company) {
		if(company.isDeleted()) {
			Locale locale = LocaleContextHolder.getLocale();
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, "An error occured", locale));
		}
	}
	
	public void isPassive(Company company) {
		if(!company.isActive()) {
			Locale locale = LocaleContextHolder.getLocale();
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, "An error occured", locale));
		}
	}
	
}
