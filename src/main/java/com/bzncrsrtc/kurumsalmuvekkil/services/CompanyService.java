package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ThereIsNoCompanyException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;
import com.bzncrsrtc.kurumsalmuvekkil.rules.CompanyRules;
import com.bzncrsrtc.kurumsalmuvekkil.rules.FileRules;
import com.bzncrsrtc.kurumsalmuvekkil.rules.LawyerRules;
import com.bzncrsrtc.kurumsalmuvekkil.rules.SubscriptionRules;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final MessageSource messageSource;
	
	public CompanyService(CompanyRepository companyRepository, MessageSource messageSource) {
		this.companyRepository = companyRepository;
		this.messageSource = messageSource;
	}
	
	public List<Company> findAll(Locale locale){
		List<Company> companies = companyRepository.findAllByDeletedAndActive(false, true);

		if(companies.isEmpty()) {
			throw new ThereIsNoCompanyException(messageSource.getMessage("there.is.no.company.message", null, locale));
		}

		return companies;
	}
	
	public Company findById(UUID id, Locale locale) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(id, false, true);

		if(company.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		return company.get();
	}
	
	public Company create(Company company, Locale locale) {
		company.setCreatedAt(LocalDateTime.now());
		company.setUpdatedAt(LocalDateTime.now());
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public void update(Company company, Locale locale) {

		if(!companyRepository.existsById(company.getId())) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		company.setUpdatedAt(LocalDateTime.now());
		companyRepository.save(company);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Company> optionalCompany = companyRepository.findById(id);

		if(optionalCompany.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		Company company = optionalCompany.get();
		company.setDeleted(true);
		company.setDeletedAt(LocalDateTime.now());
		companyRepository.save(company);
	}
	
	public Subscription getSubscription(UUID companyId, Locale locale) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);

		if(company.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		Subscription subscription = company.get().getSubscription();
		return subscription;
	}
	
	public List<Lawyer> getLawyers(UUID companyId, Locale locale){
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);

		if(company.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		List<Lawyer> lawyers = company.get().getLawyers();
		return lawyers;
	}
	
	public List<File> getFiles(UUID companyId, Locale locale){
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);

		if(company.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		List<File> files = company.get().getFiles();
		return files;
	}
	
}
