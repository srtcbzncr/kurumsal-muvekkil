package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
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
		return companyRepository.findAllByDeleted(false);
	}
	
	public List<Company> findAllActive(Locale locale) {
		return companyRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Company> findAllPassive(Locale locale){
		return companyRepository.findAllByDeletedAndActive(false, false);
	}
	
	public List<Company> findAllDeleted(Locale locale){
		return companyRepository.findAllByDeleted(true);
	}
	
	public Company findById(UUID id, Locale locale) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Company> company;

		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			company = companyRepository.findById(id);
		}
		else {
			company = companyRepository.findByIdAndDeletedAndActive(id, false, true);
		}
				
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
		if(companyRepository.existsByNameAndDeleted(company.getName(), false)) {
			throw new CompanyExistsException(messageSource.getMessage("company.exists.message", null, locale));
		}
		
		if(companyRepository.existsByTaxNoAndDeleted(company.getTaxNo(), false)) {
			throw new CompanyExistsException(messageSource.getMessage("company.exists.message", null, locale));
		}
		
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public Company update(Company company, Locale locale) {
		if(!companyRepository.existsByIdAndDeleted(company.getId(), false)) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		if(companyRepository.existsByIdNotAndNameAndDeleted(company.getId(), company.getName(), false)) {
			throw new CompanyExistsException(messageSource.getMessage("company.exists.message", null, locale));
		}
		
		if(companyRepository.existsByIdNotAndTaxNoAndDeleted(company.getId(), company.getTaxNo(), false)) {
			throw new CompanyExistsException(messageSource.getMessage("company.exists.message", null, locale));
		}
		
		return companyRepository.save(company);
	}
	
	public Company setActive(UUID id, Locale locale) {
		Optional<Company> optionalCompany = companyRepository.findByIdAndDeleted(id, false);
		
		if(optionalCompany.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		Company company = optionalCompany.get();
		company.setActive(true);
		
		return companyRepository.save(company);
	}
	
	public Company setPassive(UUID id, Locale locale) {
		Optional<Company> optionalCompany = companyRepository.findByIdAndDeleted(id, false);
		
		if(optionalCompany.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		Company company = optionalCompany.get();
		company.setActive(false);
		
		return companyRepository.save(company);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Company> optionalCompany = companyRepository.findByIdAndDeleted(id, false);
		
		if(optionalCompany.isEmpty()) {
			throw new CompanyNotFoundException(messageSource.getMessage("company.not.found.message", null, locale));
		}
		
		Company company = optionalCompany.get();
		company.setDeleted(true);
		company.setDeletedAt(LocalDateTime.now());
		companyRepository.save(company);
	}	
	
	public int allCount(Locale locale) {
		return companyRepository.countByDeleted(false);
	}
	
	public int activeCount(Locale locale) {
		return companyRepository.countByActiveAndDeleted(true, false);
	}
	
	public int passiveCount(Locale locale) {
		return companyRepository.countByActiveAndDeleted(false, false);
	}
	
	public int deletedCount(Locale locale) {
		return companyRepository.countByDeleted(true);
	}
}
