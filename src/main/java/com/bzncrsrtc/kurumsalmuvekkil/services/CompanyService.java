package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
	private final CompanyRules companyRules;
	private final LawyerRules lawyerRules;
	private final SubscriptionRules subscriptionRules;
	private final FileRules fileRules;
	
	public CompanyService(CompanyRepository companyRepository, CompanyRules companyRules, LawyerRules lawyerRules, SubscriptionRules subscriptionRules, FileRules fileRules) {
		this.companyRepository = companyRepository;
		this.companyRules = companyRules;
		this.lawyerRules = lawyerRules;
		this.subscriptionRules = subscriptionRules;
		this.fileRules = fileRules;
	}
	
	public List<Company> findAll(Locale locale){
		List<Company> companies = companyRepository.findAllByDeletedAndActive(false, true);
		companyRules.isNull(companies, locale);
		return companies;
	}
	
	public Company findById(UUID id, Locale locale) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(id, false, true);
		companyRules.isNull(company, locale);
		return company.get();
	}
	
	public Company create(Company company, Locale locale) {
		company.setCreatedAt(LocalDateTime.now());
		company.setUpdatedAt(LocalDateTime.now());
		Company savedCompany = companyRepository.save(company);
		return savedCompany;
	}
	
	public void update(Company company, Locale locale) {
		companyRules.isThere(company.getId(), locale);
		company.setUpdatedAt(LocalDateTime.now());
		companyRepository.save(company);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Company> optionalCompany = companyRepository.findById(id);
		companyRules.isNull(optionalCompany, locale);
		Company company = optionalCompany.get();
		company.setDeleted(true);
		company.setDeletedAt(LocalDateTime.now());
		companyRepository.save(company);
	}
	
	public Subscription getSubscription(UUID companyId, Locale locale) {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);
		companyRules.isNull(company, locale);
		Subscription subscription = company.get().getSubscription();
		subscriptionRules.isNull(subscription, locale);
		subscriptionRules.isDeleted(subscription, locale);
		subscriptionRules.isPassive(subscription, locale);
		return subscription;
	}
	
	public List<Lawyer> getLawyers(UUID companyId, Locale locale){
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);
		companyRules.isNull(company, locale);
		List<Lawyer> lawyers = company.get().getLawyers();
		lawyerRules.isNull(lawyers, locale);
		return lawyers;
	}
	
	public List<File> getFiles(UUID companyId, Locale locale){
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(companyId, false, true);
		companyRules.isNull(company, locale);
		List<File> files = company.get().getFiles();
		fileRules.isNull(files, locale);
		return files;
	}
	
}
