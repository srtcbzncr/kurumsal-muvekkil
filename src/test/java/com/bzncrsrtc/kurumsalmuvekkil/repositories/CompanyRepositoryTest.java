package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;

@DataJpaTest
public class CompanyRepositoryTest {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	Company passiveCompany = new Company("Company 1");
	Company deletedCompany = new Company("Company 2");
	Company deletedAndPassiveCompany = new Company("Company 3");
	Company activeCompany1 = new Company("Company 4");
	Company activeCompany2 = new Company("Company 5");
	
	@BeforeEach
	public void init() {
		passiveCompany.setActive(false);
		passiveCompany = companyRepository.save(passiveCompany);
		
		deletedCompany.setDeleted(true);
		deletedCompany.setDeletedAt(LocalDateTime.now());
		deletedCompany = companyRepository.save(deletedCompany);
		
		deletedAndPassiveCompany.setActive(false);
		deletedAndPassiveCompany.setDeleted(true);
		deletedAndPassiveCompany.setDeletedAt(LocalDateTime.now());
		deletedAndPassiveCompany = companyRepository.save(deletedAndPassiveCompany);
		
		activeCompany1 = companyRepository.save(activeCompany1);
		activeCompany2 = companyRepository.save(activeCompany2);
	}
	
	@Test
	public void findAllTest() {
		List<Company> allCompanies = companyRepository.findAll();
		
		assertEquals(5, allCompanies.size());
	}	
	
	@Test
	public void findAllByDeletedAndActiveTest_findNotDeletedAndActiveCompanies() {
		List<Company> allCompanies = companyRepository.findAllByDeletedAndActive(false, true);
		
		assertEquals(2, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(true, company.isActive());
			assertEquals(false, company.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_findDeletedAndActiveCompanies() {
		List<Company> allCompanies = companyRepository.findAllByDeletedAndActive(true, true);
		
		assertEquals(1, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(true, company.isActive());
			assertEquals(true, company.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_findNotDeletedAndPassiveCompanies() {
		List<Company> allCompanies = companyRepository.findAllByDeletedAndActive(false, false);
		
		assertEquals(1, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(false, company.isActive());
			assertEquals(false, company.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_findDeletedAndPassiveCompanies() {
		List<Company> allCompanies = companyRepository.findAllByDeletedAndActive(true, false);
		
		assertEquals(1, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(false, company.isActive());
			assertEquals(true, company.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedTest_findNotDeletedCompanies() {
		List<Company> allCompanies = companyRepository.findAllByDeleted(false);
		
		assertEquals(3, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(false, company.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedTest_findDeletedCompanies() {
		List<Company> allCompanies = companyRepository.findAllByDeleted(true);
		
		assertEquals(2, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(true, company.isDeleted());
		}
	}
	
	@Test
	public void findAllByActiveTest_findActiveCompanies() {
		List<Company> allCompanies = companyRepository.findAllByActive(true);
		
		assertEquals(3, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(true, company.isActive());
		}
	}
	
	@Test
	public void findAllByActiveTest_findPassiveCompanies() {
		List<Company> allCompanies = companyRepository.findAllByActive(false);
		
		assertEquals(2, allCompanies.size());
		for(Company company : allCompanies){
			assertEquals(false, company.isActive());
		}
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest_NotDeletedAndActiveCompany() {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(activeCompany1.getId(), false, true);
		
		assertEquals(false, company.isEmpty());
		assertEquals(true, company.get().isActive());
		assertEquals(false, company.get().isDeleted());
		assertEquals(company.hashCode(), activeCompany1.hashCode());
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest_NotDeletedAndPassiveCompany() {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(passiveCompany.getId(), false, true);
		
		assertEquals(true, company.isEmpty());
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest_DeletedAndPassiveCompany() {
		Optional<Company> company = companyRepository.findByIdAndDeletedAndActive(deletedCompany.getId(), false, true);
		
		assertEquals(true, company.isEmpty());
	}

}
