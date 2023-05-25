package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	public void findAllDeleted() {
		// Mock the repository
		when(companyRepository.findAllByDeletedAndActive(true, true)).thenReturn(List.of(new Company(), new Company()));
		
		
	}
	
}
