package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PlanNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.PlanRepository;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

	@InjectMocks
	private PlanService planService;
	
	@Mock
	private PlanRepository planRepository;
	
	@Mock
	private MessageSource messageSource;
	

	@Test
	public void findAllTest() {
		// Mock the repository
		when(planRepository.findAllByDeletedAndActive(false, true)).thenReturn(List.of(new Plan(), new Plan()));
		
		// Service call
		List<Plan> plansFromService = planService.findAll(Locale.US);
		
		// Assertions
		assertEquals(2, plansFromService.size());
		
		// Verification
		verify(planRepository, times(1)).findAllByDeletedAndActive(false, true);
		
	}
	
	@Test
	public void findByIdTest_ExistingPlan_ShouldReturnPlan() {
		// Generate a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(planRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Plan()));
		
		// Assertions
		assertDoesNotThrow(() -> planService.findById(id, Locale.US));
		assertNotNull(planService.findById(id, Locale.US));
		
		// Verifications
		verify(planRepository, times(2)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findByIdTest_NotExistingPlan_ShouldCausePlanNotFoundException() {
		// Generate a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(planRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(PlanNotFoundException.class, () -> planService.findById(id, Locale.US));
		
		// Verifications
		verify(planRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findAllDeletedTest() {
		// Mock the repository
		when(planRepository.findAllByDeleted(true)).thenReturn(List.of(new Plan(), new Plan()));
		
		// Service call
		List<Plan> plans = planService.findAllDeleted(Locale.US);
		
		// Assertions
		assertEquals(2, plans.size());
		
		// Verifications
		verify(planRepository, times(1)).findAllByDeleted(true);
	}
	
	@Test
	public void findAllPassiveTest() {
		// Mock the repository
		when(planRepository.findAllByDeletedAndActive(false, false)).thenReturn(List.of(new Plan(), new Plan()));
		
		// Service call
		List<Plan> plans = planService.findAllPassive(Locale.US);
		
		// Assertions
		assertEquals(2, plans.size());
		
		// Verifications
		verify(planRepository, times(1)).findAllByDeletedAndActive(false, false);
	}
	
	@Test
	public void createTest() {
		// Create a plan
		Plan plan = new Plan();
		plan.setId(UUID.randomUUID());
		
		// Mock the repository
		when(planRepository.save(any(Plan.class))).thenReturn(plan);
		
		// Service call
		Plan savedPlan = planService.create(plan, Locale.US);
		
		// Assertions
		assertEquals(plan.hashCode(), savedPlan.hashCode());
		
		// Verifications
		verify(planRepository, times(1)).save(any(Plan.class));
	}
	
	@Test
	public void updateTest_ExistingPlan_ShouldNotCausePlanNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a plan
		Plan plan = new Plan();
		plan.setId(id);
		
		// Mock the repository
		when(planRepository.existsByIdAndDeleted(id, false)).thenReturn(true);
		
		// Assertions
		assertDoesNotThrow(() -> planService.update(plan, Locale.US));
		
		// Verifications
		verify(planRepository, times(1)).existsByIdAndDeleted(id, false);
		verify(planRepository, times(1)).save(plan);	
	}
	
	@Test
	public void updateTest_NotExistingPlan_ShouldCausePlanNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a plan
		Plan plan = new Plan();
		plan.setId(id);
		
		// Mock the repository
		when(planRepository.existsByIdAndDeleted(id, false)).thenReturn(false);
		
		// Assertions
		assertThrows(PlanNotFoundException.class, () -> planService.update(plan, Locale.US));
		
		// Verifications
		verify(planRepository, times(1)).existsByIdAndDeleted(id, false);
		verify(planRepository, times(0)).save(plan);	
	}
	
	@Test
	public void deleteTest_ExistingPlan_ShouldNotCausePlanNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(planRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.of(new Plan()));
		
		// Assertions
		assertDoesNotThrow(() -> planService.delete(id, Locale.US));
		
		// Verifications
		verify(planRepository, times(1)).findByIdAndDeleted(id, false);
		verify(planRepository, times(1)).save(any(Plan.class));	
	}
	
	@Test
	public void deleteTest_NotExistingPlan_ShouldCausePlanNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(planRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(PlanNotFoundException.class, () -> planService.delete(id, Locale.US));
		
		// Verifications
		verify(planRepository, times(1)).findByIdAndDeleted(id, false);
		verify(planRepository, times(0)).save(any(Plan.class));	
	}
}
