package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

	@InjectMocks
	private CompanyService companyService;
	
	@Mock
	private CompanyRepository companyRepository;
	
	@Mock
	private MessageSource messageSource;

	@Test
	public void findAllTest() {		
		// Mock the repository
		when(companyRepository.findAllByDeletedAndActive(false, true)).thenReturn(List.of(new Company(), new Company(), new Company()));
		
		// Service call
		List<Company> companiesFromService = companyService.findAll(Locale.US);
		
		// Assertions
		assertEquals(3, companiesFromService.size());
		
		// Verifications
		verify(companyRepository, times(1)).findAllByDeletedAndActive(false, true);
	}
	
	@Test
	public void findByIdTest_ExistingCompany_ShouldReturnCompany() {
		// Generate a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Company()));
		
		// Assertions
		assertDoesNotThrow(() -> companyService.findById(id, Locale.US));
		assertNotNull(companyService.findById(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(2)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findByIdTest_NotExistingCompany_ShouldCauseCompanyNotFoundException() {
		// Generate a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyService.findById(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findAllDeletedTest() {
		// Mock the repository
		when(companyRepository.findAllByDeleted(true)).thenReturn(List.of(new Company(), new Company()));
		
		// Service call
		List<Company> companies = companyService.findAllDeleted();
		
		// Assertions
		assertEquals(2, companies.size());
		
		// Verifications
		verify(companyRepository, times(1)).findAllByDeleted(true);
	}
	
	@Test
	public void findAllPassiveTest() {
		// Mock the repository
		when(companyRepository.findAllByDeletedAndActive(false, false)).thenReturn(List.of(new Company(), new Company()));
		
		// Service call
		List<Company> companies = companyService.findAllPassive();
		
		// Assertions
		assertEquals(2, companies.size());
		
		// Verifications
		verify(companyRepository, times(1)).findAllByDeletedAndActive(false, false);
	}
	
	@Test
	public void createTest_NotExistingCompanyName_ShouldNotCauseCompanyExistsException() {
		// Create a company
		Company company = new Company();
		company.setId(UUID.randomUUID());
		
		// Mock the repository
		when(companyRepository.existsByNameAndDeleted(any(), anyBoolean())).thenReturn(false);
		when(companyRepository.save(any(Company.class))).thenReturn(company);
		
		// Service call
		Company savedCompany = companyService.create(company, Locale.US);
		
		// Assertions
		assertDoesNotThrow(() -> companyService.create(company, Locale.US));
		assertEquals(company.hashCode(), savedCompany.hashCode());
		
		// Verifications
		verify(companyRepository, times(2)).save(any(Company.class));
	}
	
	@Test
	public void createTest_ExistingCompanyName_ShouldCauseCompanyExistsException() {
		// Create a company
		Company company = new Company();
		company.setId(UUID.randomUUID());
		
		// Mock the repository
		when(companyRepository.existsByNameAndDeleted(any(), anyBoolean())).thenReturn(true);
		
		// Assertions
		assertThrows(CompanyExistsException.class, () -> companyService.create(company, Locale.US));
		
		// Verifications
		verify(companyRepository, times(0)).save(any(Company.class));
	}
	
	@Test
	public void updateTest_ExistingCompany_ShouldNotCauseCompanyNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a company
		Company company = new Company();
		company.setId(id);
		
		// Mock the repository
		when(companyRepository.existsByIdAndDeleted(id, false)).thenReturn(true);
		
		// Assertions
		assertDoesNotThrow(() -> companyService.update(company, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).existsByIdAndDeleted(id, false);
		verify(companyRepository, times(1)).save(company);	
	}
	
	@Test
	public void updateTest_NotExistingCompany_ShouldCauseCompanyNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a company
		Company company = new Company();
		company.setId(id);
		
		// Mock the repository
		when(companyRepository.existsByIdAndDeleted(id, false)).thenReturn(false);
		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyService.update(company, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).existsByIdAndDeleted(id, false);
		verify(companyRepository, times(0)).save(company);	
	}
	
	@Test
	public void deleteTest_ExistingCompany_ShouldNotCauseNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findById(id)).thenReturn(Optional.of(new Company()));
		
		// Assertions
		assertDoesNotThrow(() -> companyService.delete(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findById(id);
		verify(companyRepository, times(1)).save(any(Company.class));	
	}
	
	@Test
	public void deleteTest_NotExistingCompany_ShouldCauseNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findById(id)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyService.delete(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findById(id);
		verify(companyRepository, times(0)).save(any(Company.class));	
	}
	
	@Test
	public void getSubscriptionTest_ExistingCompany_ShouldNotCauseCompanyNotFoundException() {
		// Create a random UUID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Company()));
		
		// Assertions
		assertDoesNotThrow(() -> companyService.getSubscription(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void getSubscriptionTest_NotExistingCompany_ShouldCauseCompanyNotFoundException() {
		// Create a random UUID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyService.getSubscription(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void getLawyersTest_ExistingCompany_ShouldNotCauseCompanyNotFoundException() {
		// Create a random UUID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Company()));
		
		// Assertions
		assertDoesNotThrow(() -> companyService.getLawyers(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void getLawyersTest_NotExistingCompany_ShouldCauseCompanyNotFoundException() {
		// Create a random UUID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyService.getLawyers(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void getFilesTest_ExistingCompany_ShouldNotCauseCompanyNotFoundException() {
		// Create a random UUID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Company()));
		
		// Assertions
		assertDoesNotThrow(() -> companyService.getFiles(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void getFilesTest_NotExistingCompany_ShouldCauseCompanyNotFoundException() {
		// Create a random UUID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(companyRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CompanyNotFoundException.class, () -> companyService.getFiles(id, Locale.US));
		
		// Verifications
		verify(companyRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
}
