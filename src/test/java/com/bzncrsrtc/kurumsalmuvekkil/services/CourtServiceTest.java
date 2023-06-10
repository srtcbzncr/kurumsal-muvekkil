package com.bzncrsrtc.kurumsalmuvekkil.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CourtRepository;

@ExtendWith(MockitoExtension.class)
class CourtServiceTest {

	@InjectMocks
	private CourtService courtService;
	
	@Mock
	private CourtRepository courtRepository;
	
	@Mock
	private MessageSource messageSource;
	
	@Test
	public void findAllTest() {
		// Mock the repository
		when(courtRepository.findAllByDeletedAndActive(false, true)).thenReturn(List.of(new Court(), new Court()));
		
		// Service call
		List<Court> courtsFromService = courtService.findAll(Locale.US);
		
		// Assertions
		assertEquals(2, courtsFromService.size());
		
		// Verification
		verify(courtRepository, times(1)).findAllByDeletedAndActive(false, true);
	}
	
	@Test
	public void findByIdTest_ExistingCourt_ShouldReturnCourt() {
		// Generate a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(courtRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Court()));
		
		// Assertions
		assertDoesNotThrow(() -> courtService.findById(id, Locale.US));
		assertNotNull(courtService.findById(id, Locale.US));
		
		// Verifications
		verify(courtRepository, times(2)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findByIdTest_NotExistingCourt_ShouldCauseCourtNotFoundException() {
		// Generate a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(courtRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CourtNotFoundException.class, () -> courtService.findById(id, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findAllDeletedTest() {
		// Mock the repository
		when(courtRepository.findAllByDeleted(true)).thenReturn(List.of(new Court(), new Court()));
		
		// Service call
		List<Court> courts = courtService.findAllDeleted(Locale.US);
		
		// Assertions
		assertEquals(2, courts.size());
		
		// Verifications
		verify(courtRepository, times(1)).findAllByDeleted(true);
	}
	
	@Test
	public void findAllPassiveTest() {
		// Mock the repository
		when(courtRepository.findAllByDeletedAndActive(false, false)).thenReturn(List.of(new Court(), new Court()));
		
		// Service call
		List<Court> courts = courtService.findAllPassive(Locale.US);
		
		// Assertions
		assertEquals(2, courts.size());
		
		// Verifications
		verify(courtRepository, times(1)).findAllByDeletedAndActive(false, false);
	}
	
	@Test
	public void createTest_NotExistingCourt_ShouldNotCauseCourtExistsException() {
		// Create a court
		Court court = new Court("Court");
		
		// Create court returned by courtRepository.save();
		Court courtWithId = new Court("Court");
		UUID id = UUID.randomUUID();
		courtWithId.setId(id);
		
		// Mock the repository
		when(courtRepository.existsByNameAndDeleted(court.getName(), false)).thenReturn(false);
		when(courtRepository.save(court)).thenReturn(courtWithId);
		
		// Service call
		Court savedCourt = courtService.create(court, Locale.US);
		
		// Assertions
		assertDoesNotThrow(() -> courtService.create(court, Locale.US));
		assertEquals(courtWithId.hashCode(), savedCourt.hashCode());
		
		// Verifications
		verify(courtRepository, times(2)).existsByNameAndDeleted(court.getName(), false);
		verify(courtRepository, times(2)).save(court);
		
	}
	
	@Test
	public void createTest_ExistingCourt_ShouldCauseCourtExistsException() {
		// Create a court
		Court court = new Court("Court");
		
		// Mock the repository
		when(courtRepository.existsByNameAndDeleted(court.getName(), false)).thenReturn(true);
		
		// Assertions
		assertThrows(CourtExistsException.class, () -> courtService.create(court, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).existsByNameAndDeleted(court.getName(), false);
		verify(courtRepository, times(0)).save(court);
		
	}
	
	@Test
	public void addTest_ExistingCourt_ShouldCauseCourtExistsException() {
		// Create a court
		Court court = new Court("Court");
		
		// Generate random parent ID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(courtRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(new Court("Parent")));
		when(courtRepository.existsByNameAndDeleted(court.getName(), false)).thenReturn(true);
		
		// Assertions
		assertThrows(CourtExistsException.class, () -> courtService.add(id, court, Locale.US));
		
		// Verifications
		verify(courtRepository, times(0)).save(court);
	}
	
	@Test
	public void addTest_NotExistingParentCourt_ShouldCauseCourtNotFoundException() {
		// Create a court
		Court court = new Court("Court");
		
		// Generate random parent ID
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(courtRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CourtNotFoundException.class, () -> courtService.add(id, court, Locale.US));
		
		// Verifications
		verify(courtRepository, times(0)).save(court);
	}
	
	@Test
	public void addTest_ExistingParentAndNotExistingCourt_ShouldNotCauseException() {
		// Create a court
		Court court = new Court("Court");
		
		// Generate random parent ID
		UUID id = UUID.randomUUID();
		
		// Create parent
		Court parent = new Court("Parent");
		parent.setId(id);
		
		// Mock the repository
		when(courtRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(parent));
		when(courtRepository.existsByNameAndDeleted(court.getName(), false)).thenReturn(false);
		
		// Assertions
		assertDoesNotThrow(() -> courtService.add(id, court, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).save(court);
	}
	
	@Test
	public void updateTest_ExistingCourt_ShouldNotCauseCourtNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a court
		Court court = new Court();
		court.setId(id);
		
		// Mock the repository
		when(courtRepository.existsByIdAndDeleted(id, false)).thenReturn(true);
		
		// Assertions
		assertDoesNotThrow(() -> courtService.update(court, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).existsByIdAndDeleted(id, false);
		verify(courtRepository, times(1)).save(court);	
	}
	
	@Test
	public void updateTest_NotExistingCourt_ShouldCauseCourtNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a court
		Court court = new Court();
		court.setId(id);
		
		// Mock the repository
		when(courtRepository.existsByIdAndDeleted(id, false)).thenReturn(false);
		
		// Assertions
		assertThrows(CourtNotFoundException.class, () -> courtService.update(court, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).existsByIdAndDeleted(id, false);
		verify(courtRepository, times(0)).save(court);	
	}
	
	@Test
	public void deleteTest_ExistingCourt_ShouldNotCauseCourtNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(courtRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.of(new Court()));
		
		// Assertions
		assertDoesNotThrow(() -> courtService.delete(id, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).findByIdAndDeleted(id, false);
		verify(courtRepository, times(1)).save(any(Court.class));	
	}
	
	@Test
	public void deleteTest_NotExistingCourt_ShouldCauseCourtNotFoundException() {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(courtRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(CourtNotFoundException.class, () -> courtService.delete(id, Locale.US));
		
		// Verifications
		verify(courtRepository, times(1)).findByIdAndDeleted(id, false);
		verify(courtRepository, times(0)).save(any(Court.class));	
	}
}
