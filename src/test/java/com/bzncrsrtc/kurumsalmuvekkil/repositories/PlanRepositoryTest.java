package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;

@DataJpaTest
class PlanRepositoryTest {

	@Autowired
	private PlanRepository planRepository;
	
	Plan passivePlan = new Plan("Passive Plan", "desc", new BigDecimal(10), new BigDecimal(10), 10, 10, 10);
	Plan activePlan = new Plan("Active Plan", "desc", new BigDecimal(10), new BigDecimal(10), 10, 10, 10);
	Plan deletedPlan = new Plan("Deleted Plan", "desc", new BigDecimal(10), new BigDecimal(10), 10, 10, 10);
	Plan deletedAndPassivePlan = new Plan("Deleted and Passive Plan", "desc", new BigDecimal(10), new BigDecimal(10), 10, 10, 10);
	
	@BeforeEach()
	public void init() {
		passivePlan.setActive(false);
		passivePlan = planRepository.save(passivePlan);
		
		deletedPlan.setDeleted(true);
		deletedPlan.setDeletedAt(LocalDateTime.now());
		deletedPlan = planRepository.save(deletedPlan);
		
		deletedAndPassivePlan.setActive(false);
		deletedAndPassivePlan.setDeleted(true);
		deletedAndPassivePlan.setDeletedAt(LocalDateTime.now());
		deletedAndPassivePlan = planRepository.save(deletedAndPassivePlan);
		
		activePlan = planRepository.save(activePlan);
	}
	
	@Test
	public void findAllTest() {
		// Repository call
		List<Plan> plans = planRepository.findAll();
		
		// Assertions
		assertEquals(4, plans.size());
	}
	
	@Test
	public void findAllByDeletedTest_ShouldReturnNotDeletedPlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByDeleted(false);
		
		// Assertions
		assertEquals(2, plans.size());
		for(Plan plan : plans) {
			assertFalse(plan.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedTest_ShouldReturnDeletedPlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByDeleted(true);
		
		// Assertions
		assertEquals(2, plans.size());
		for(Plan plan : plans) {
			assertTrue(plan.isDeleted());
		}
	}
	
	@Test
	public void findAllByActiveTest_ShouldReturnPassivePlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByActive(false);
		
		// Assertions
		assertEquals(2, plans.size());
		for(Plan plan : plans) {
			assertFalse(plan.isActive());
		}
	}
	
	@Test
	public void findAllByActiveTest_ShouldReturnActivePlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByActive(true);
		
		// Assertions
		assertEquals(2, plans.size());
		for(Plan plan : plans) {
			assertTrue(plan.isActive());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnDeletedAndActivePlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByDeletedAndActive(true, true);
		
		// Assertions
		assertEquals(1, plans.size());
		for(Plan plan : plans) {
			assertTrue(plan.isActive());
			assertTrue(plan.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnNotDeletedAndActivePlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByDeletedAndActive(false, true);
		
		// Assertions
		assertEquals(1, plans.size());
		for(Plan plan : plans) {
			assertTrue(plan.isActive());
			assertFalse(plan.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnDeletedAndPassivePlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByDeletedAndActive(true, false);
		
		// Assertions
		assertEquals(1, plans.size());
		for(Plan plan : plans) {
			assertFalse(plan.isActive());
			assertTrue(plan.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnNotDeletedAndPassivePlans() {
		// Repository call
		List<Plan> plans = planRepository.findAllByDeletedAndActive(false, false);
		
		// Assertions
		assertEquals(1, plans.size());
		for(Plan plan : plans) {
			assertFalse(plan.isActive());
			assertFalse(plan.isDeleted());
		}
	}
	
	@Test
	public void findByIdTest() {
		// Repository calls
		Optional<Plan> passive = planRepository.findById(passivePlan.getId());
		Optional<Plan> active = planRepository.findById(activePlan.getId());
		Optional<Plan> deleted = planRepository.findById(deletedPlan.getId());
		Optional<Plan> deletedAndPassive = planRepository.findById(deletedAndPassivePlan.getId());
		
		// Assertions
		assertFalse(passive.isEmpty());
		assertFalse(active.isEmpty());
		assertFalse(deleted.isEmpty());
		assertFalse(deletedAndPassive.isEmpty());
	}
	
	@Test
	public void findByIdAndDeletedTest() {
		
	}
	
	@Test
	public void findByIdAndActiveTest() {
		
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest() {
		
	}
	
	@Test
	public void existsByIdAndDeletedTest() {
		
	}

}
