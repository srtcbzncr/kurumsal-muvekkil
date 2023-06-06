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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ClientNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.IdentificationNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PhoneNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.ClientRepository;

class ClientServiceTest {

	@InjectMocks
	private ClientService clientService;
	
	@Mock
	private ClientRepository clientRepository;
	
	@Mock
	private MessageSource messageSource;
	
	@Mock
	private UserService userService;
	
	@Test
	public void findAllTest() {
		// Mock the repository
		when(clientRepository.findAllByDeletedAndActive(false, true)).thenReturn(List.of(new Client(), new Client()));
		
		// Service call
		List<Client> clients = clientService.findAll(Locale.US);
		
		// Assertions
		assertEquals(2, clients.size());
		
		// Verifications
		verify(clientRepository.findAllByDeletedAndActive(false, true), times(1));
	}
	
	@Test
	public void findAllDeletedTest() {
		// Mock the repository
		when(clientRepository.findAllByDeletedAndActive(false, true)).thenReturn(List.of(new Client(), new Client()));
		
		// Service call
		List<Client> clients = clientService.findAll(Locale.US);
		
		// Assertions
		assertEquals(2, clients.size());
		
		// Verifications
		verify(clientRepository.findAllByDeleted(true), times(1));
	}
	
	@Test
	public void findAllPassiveTest() {
		// Mock the repository
		when(clientRepository.findAllByDeletedAndActive(false, true)).thenReturn(List.of(new Client(), new Client()));
		
		// Service call
		List<Client> clients = clientService.findAll(Locale.US);
		
		// Assertions
		assertEquals(2, clients.size());
		
		// Verifications
		verify(clientRepository.findAllByDeletedAndActive(false, false), times(1));
	}
	
	@Test
	public void findByIdTest_ExistingClient_ShouldNotCauseClientNotFoundException() {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Create a Client
		Client client = new Client();
		client.setId(id);
		
		// Mock the repository
		when(clientRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.of(client));
		
		// Service call
		Client clientFromService = clientService.findById(id, Locale.US);
		
		// Assertions
		assertDoesNotThrow(() -> clientService.findById(id, Locale.US));
		assertEquals(id, client.getId());
		
		// Verifications
		verify(clientRepository.findByIdAndDeletedAndActive(id, false, true), times(1));
	}
	
	@Test
	public void findByIdTest_NotExistingClient_ShouldCauseClientNotFoundException() {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(clientRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Service call
		Client clientFromService = clientService.findById(id, Locale.US);
		
		// Assertions
		assertThrows(ClientNotFoundException.class, () -> clientService.findById(id, Locale.US));
		
		// Verifications
		verify(clientRepository.findByIdAndDeletedAndActive(id, false, true), times(1));
	}
	
	@Test
	public void createTest_NotExistingIdentificationNumberAndNotExistingPhoneNumber_ShouldNotCauseException() {
		// Create a client
		Client client = new Client("Identification Number", "Firstname", "Lastname", "Phone");
		
		// Mock the repository
		when(clientRepository.existsByIdentificationNumber(any())).thenReturn(false);
		when(clientRepository.existsByPhone(any())).thenReturn(false);
		when(clientRepository.save(any())).thenReturn(client);
		
		// Assertions
		assertDoesNotThrow(() -> clientService.create(client, Locale.US));
		
		// Verifications
		verify(clientRepository.existsByIdentificationNumber(client.getIdentificationNumber()), times(1));
		verify(clientRepository.existsByPhone(client.getPhone()), times(1));
		verify(clientRepository.save(client), times(1));
	}
	
	@Test
	public void createTest_ExistingIdentificationNumberAndNotExistingPhoneNumber_ShouldCauseIdentificationNumberAlreadyUsedException() {
		// Create a client
		Client client = new Client("Identification Number", "Firstname", "Lastname", "Phone");
		
		// Mock the repository
		when(clientRepository.existsByIdentificationNumber(any())).thenReturn(true);
		when(clientRepository.existsByPhone(any())).thenReturn(false);
		when(clientRepository.save(any())).thenReturn(client);
		
		// Assertions
		assertThrows(IdentificationNumberAlreadyUsedException.class, () -> clientService.create(client, Locale.US));
		// Verifications
		verify(clientRepository.existsByIdentificationNumber(client.getIdentificationNumber()), times(1));
		verify(clientRepository.existsByPhone(client.getPhone()), times(1));
		verify(clientRepository.save(client), times(0));
	}
	
	@Test
	public void createTest_NotExistingIdentificationNumberAndExistingPhoneNumber_ShouldCausePhoneNumberAlreadyUsedException() {
		// Create a client
		Client client = new Client("Identification Number", "Firstname", "Lastname", "Phone");
		
		// Mock the repository
		when(clientRepository.existsByIdentificationNumber(any())).thenReturn(false);
		when(clientRepository.existsByPhone(any())).thenReturn(true);
		when(clientRepository.save(any())).thenReturn(client);
		
		// Assertions
		assertThrows(PhoneNumberAlreadyUsedException.class, () -> clientService.create(client, Locale.US));
		
		// Verifications
		verify(clientRepository.existsByIdentificationNumber(client.getIdentificationNumber()), times(1));
		verify(clientRepository.existsByPhone(client.getPhone()), times(1));
		verify(clientRepository.save(client), times(0));
	}
	

}
