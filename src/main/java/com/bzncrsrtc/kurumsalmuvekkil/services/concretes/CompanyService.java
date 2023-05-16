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
		return companyRepository.findAllByDeletedAndActive(false, true);
	}
	
	public Company findById(UUID id) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(id, false, true);
		companyRules.isNull(company);
		return company.get();
	}
	
	public Company create(Company company) {
		company.setCreatedAt(LocalDateTime.now());
		company.setUpdatedAt(LocalDateTime.now());
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public Company update(Company company) {
		companyRules.isThere(company.getId());
		company.setUpdatedAt(LocalDateTime.now());
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public void delete(UUID id) {
		Optional<Company> optionalCompany = companyRepository.findById(id);
		companyRules.isNull(optionalCompany);
		Company company = optionalCompany.get();
		company.setDeleted(true);
		company.setDeletedAt(LocalDateTime.now());
		companyRepository.save(company);
	}
	
	public Subscription getSubscription(UUID companyId) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);
		companyRules.isNull(company);
		return company.get().getSubscription();
	}
	
	public List<Lawyer> getLawyers(UUID companyId){
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);
		companyRules.isNull(company);
		return company.get().getLawyers();
	}
	
	public List<File> getFiles(UUID companyId){
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);
		companyRules.isNull(company);
		return company.get().getFiles();
	}
	
}
