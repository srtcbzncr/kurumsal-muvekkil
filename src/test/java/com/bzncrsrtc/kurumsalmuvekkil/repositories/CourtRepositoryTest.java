package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bzncrsrtc.kurumsalmuvekkil.models.Court;

@DataJpaTest
class CourtRepositoryTest {

	@Autowired
	private CourtRepository courtRepository;
	
	Court passiveCourt = new Court("Passive");
	Court activeCourt = new Court("Active");
	Court deletedCourt = new Court("Deleted");
	Court deletedAndPassiveCourt = new Court("Deleted and Passive");
	
	@BeforeEach
	public void init() {
		passiveCourt.setActive(false);
		passiveCourt = courtRepository.save(passiveCourt);
		
		deletedCourt.setDeleted(true);
		deletedCourt.setDeletedAt(LocalDateTime.now());
		deletedCourt = courtRepository.save(deletedCourt);
		
		deletedAndPassiveCourt.setActive(false);
		deletedAndPassiveCourt.setDeleted(true);
		deletedAndPassiveCourt.setDeletedAt(LocalDateTime.now());
		deletedAndPassiveCourt = courtRepository.save(deletedAndPassiveCourt);
		
		activeCourt = courtRepository.save(activeCourt);
	}
	
	@Test
	public void findAllTest() {
		// Repository call
		List<Court> courts = courtRepository.findAll();
		
		// Assertions
		assertEquals(4, courts.size());
	}
	
	@Test
	public void findAllByDeletedTest_ShouldReturnNotDeletedCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByDeleted(false);
		
		// Assertions
		assertEquals(2, courts.size());
		for(Court court : courts) {
			assertFalse(court.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedTest_ShouldReturnDeletedCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByDeleted(true);
		
		// Assertions
		assertEquals(2, courts.size());
		for(Court court : courts) {
			assertTrue(court.isDeleted());
		}
	}
	
	@Test
	public void findAllByActiveTest_ShouldReturnPassiveCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByActive(false);
		
		// Assertions
		assertEquals(2, courts.size());
		for(Court court : courts) {
			assertFalse(court.isActive());
		}
	}
	
	@Test
	public void findAllByActiveTest_ShouldReturnActiveCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByActive(true);
		
		// Assertions
		assertEquals(2, courts.size());
		for(Court court : courts) {
			assertTrue(court.isActive());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnDeletedAndActiveCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByDeletedAndActive(true, true);
		
		// Assertions
		assertEquals(1, courts.size());
		for(Court court : courts) {
			assertTrue(court.isActive());
			assertTrue(court.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnNotDeletedAndActiveCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByDeletedAndActive(false, true);
		
		// Assertions
		assertEquals(1, courts.size());
		for(Court court : courts) {
			assertTrue(court.isActive());
			assertFalse(court.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnDeletedAndPassiveCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByDeletedAndActive(true, false);
		
		// Assertions
		assertEquals(1, courts.size());
		for(Court court : courts) {
			assertFalse(court.isActive());
			assertTrue(court.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_ShouldReturnNotDeletedAndPassiveCourts() {
		// Repository call
		List<Court> courts = courtRepository.findAllByDeletedAndActive(false, false);
		
		// Assertions
		assertEquals(1, courts.size());
		for(Court court : courts) {
			assertFalse(court.isActive());
			assertFalse(court.isDeleted());
		}
	}
	
	@Test
	public void findByIdTest() {
		// Repository calls
		Optional<Court> passive = courtRepository.findById(passiveCourt.getId());
		Optional<Court> active = courtRepository.findById(activeCourt.getId());
		Optional<Court> deleted = courtRepository.findById(deletedCourt.getId());
		Optional<Court> deletedAndPassive = courtRepository.findById(deletedAndPassiveCourt.getId());
		
		// Assertions
		assertFalse(passive.isEmpty());
		assertFalse(active.isEmpty());
		assertFalse(deleted.isEmpty());
		assertFalse(deletedAndPassive.isEmpty());
	}
	
	@Test
	public void findByIdAndDeletedTest() {
		// Repository calls
		Optional<Court> passive = courtRepository.findByIdAndDeleted(passiveCourt.getId(), false);
		Optional<Court> active = courtRepository.findByIdAndDeleted(activeCourt.getId(), false);
		Optional<Court> deleted = courtRepository.findByIdAndDeleted(deletedCourt.getId(), false);
		Optional<Court> deletedAndPassive = courtRepository.findByIdAndDeleted(deletedAndPassiveCourt.getId(), false);
		
		// Assertions
		assertFalse(passive.isEmpty());
		assertFalse(active.isEmpty());
		assertTrue(deleted.isEmpty());
		assertTrue(deletedAndPassive.isEmpty());
	}
	
	@Test
	public void findByIdAndActiveTest() {
		// Repository calls
		Optional<Court> passive = courtRepository.findByIdAndActive(passiveCourt.getId(), true);
		Optional<Court> active = courtRepository.findByIdAndActive(activeCourt.getId(), true);
		Optional<Court> deleted = courtRepository.findByIdAndActive(deletedCourt.getId(), true);
		Optional<Court> deletedAndPassive = courtRepository.findByIdAndActive(deletedAndPassiveCourt.getId(), true);
		
		// Assertions
		assertTrue(passive.isEmpty());
		assertFalse(active.isEmpty());
		assertFalse(deleted.isEmpty());
		assertTrue(deletedAndPassive.isEmpty());
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest() {
		// Repository calls
		Optional<Court> passive = courtRepository.findByIdAndDeletedAndActive(passiveCourt.getId(), false, true);
		Optional<Court> active = courtRepository.findByIdAndDeletedAndActive(activeCourt.getId(), false, true);
		Optional<Court> deleted = courtRepository.findByIdAndDeletedAndActive(deletedCourt.getId(), false, true);
		Optional<Court> deletedAndPassive = courtRepository.findByIdAndDeletedAndActive(deletedAndPassiveCourt.getId(), false, true);
		
		// Assertions
		assertTrue(passive.isEmpty());
		assertFalse(active.isEmpty());
		assertTrue(deleted.isEmpty());
		assertTrue(deletedAndPassive.isEmpty());
	}
	
	@Test
	public void existsByNameAndDeletedTest() {
		// Assertions
		assertTrue(courtRepository.existsByNameAndDeleted(passiveCourt.getName(), false));
		assertTrue(courtRepository.existsByNameAndDeleted(activeCourt.getName(), false));
		assertFalse(courtRepository.existsByNameAndDeleted(deletedCourt.getName(), false));
		assertFalse(courtRepository.existsByNameAndDeleted(deletedAndPassiveCourt.getName(), false));
	}
	
	@Test
	public void existsByIdAndDeletedTest() {
		// Assertions
		assertTrue(courtRepository.existsByIdAndDeleted(passiveCourt.getId(), false));
		assertTrue(courtRepository.existsByIdAndDeleted(activeCourt.getId(), false));
		assertFalse(courtRepository.existsByIdAndDeleted(deletedCourt.getId(), false));
		assertFalse(courtRepository.existsByIdAndDeleted(deletedAndPassiveCourt.getId(), false));
	}

}
