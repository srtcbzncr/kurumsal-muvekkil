package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

	@InjectMocks
	private CompanyService companyService;
	
	@Mock
	private CompanyRepository companyRepository;
	
	@Test
	public void findAllTest_NotNullListShouldReturnCompaniesList() {
		// Initialize companies list
		List<Company> companies = new ArrayList<Company>();
		companies.add(new Company("Company 1"));
		companies.add(new Company("Company 2"));
		companies.add(new Company("Company 3"));
		
		// Mock CompanyRepository
		when(companyRepository.findAllByDeletedAndActive(false, true)).thenReturn(companies);
		
		// Service call
		List<Company> companiesFromService = companyRepository.findAllByDeletedAndActive(false, true);
		
		// Assertions
		assertEquals(3, companiesFromService.size());
		
	}
	
	@Test
	public void findAllTest_NullListShouldThrowCompanyNotFoundException() {
		// Initialize companies list
		List<Company> companies = new ArrayList<Company>();
		
		// Mock CompanyRepository
		when(companyRepository.findAllByDeletedAndActive(false, true)).thenReturn(companies);

		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyRepository.findAllByDeletedAndActive(false, true));
		
	}
	
}
