package com.bzncrsrtc.kurumsalmuvekkil.services.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;
import com.bzncrsrtc.kurumsalmuvekkil.rules.CompanyRules;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final CompanyRules companyRules;
	
	public CompanyService(CompanyRepository companyRepository, CompanyRules companyRules) {
		this.companyRepository = companyRepository;
		this.companyRules = companyRules;
	}
	
	public List<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public Company findById(UUID id) {
		Optional<Company> company = companyRepository.findById(id);
		companyRules.isNull(company);
		return company.get();
	}
	
	public Company create(Company company) {
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public Company update(Company company) {
		companyRules.isThere(company.getId());
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public void delete(Company company) {
		companyRules.isThere(company.getId());
		company.setDeleted(true);
		company.setDeletedAt(LocalDateTime.now());
		companyRepository.save(company);
	}
	
	public Subscription getSubscription(UUID companyId) {
		Optional<Company> company = companyRepository.findById(companyId);
		companyRules.isNull(company);
		return company.get().getSubscription();
	}
	
	public List<Lawyer> getLawyers(UUID companyId){
		Optional<Company> company = companyRepository.findById(companyId);
		companyRules.isNull(company);
		return company.get().getLawyers();
	}
	
	public List<File> getFiles(UUID companyId){
		Optional<Company> company = companyRepository.findById(companyId);
		companyRules.isNull(company);
		return company.get().getFiles();
	}
	
}
