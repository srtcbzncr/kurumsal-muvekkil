package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	public CompanyService(CompanyRepository companyRepository, MessageSource messageSource) {
		this.companyRepository = companyRepository;
		this.messageSource = messageSource;
	}
	
	public List<Company> findAll(Locale locale){
		return companyRepository.findAllByDeletedAndActive(false, true);
	}
	
	public Company findById(UUID id, Locale locale) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(id, false, true);

		if(company.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		return company.get();
	}
	
	public List<Company> findAllDeleted(){
		return companyRepository.findAllByDeleted(true);
	}
	
	public List<Company> findAllPassive(){
		return companyRepository.findAllByDeletedAndActive(false, false);
	}
	
	public Company create(Company company, Locale locale) {
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public void update(Company company, Locale locale) {

		if(!companyRepository.existsById(company.getId())) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
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
