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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ClientNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.IdentificationNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PhoneNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
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
		verify(clientRepository, times(1)).findAllByDeletedAndActive(false, true);
	}
	
	@Test
	public void findAllDeletedTest() {
		// Mock the repository
		when(clientRepository.findAllByDeleted(true)).thenReturn(List.of(new Client(), new Client()));
		
		// Service call
		List<Client> clients = clientService.findAllDeleted(Locale.US);
		
		// Assertions
		assertEquals(2, clients.size());
		
		// Verifications
		verify(clientRepository, times(1)).findAllByDeleted(true);
	}
	
	@Test
	public void findAllPassiveTest() {
		// Mock the repository
		when(clientRepository.findAllByDeletedAndActive(false, false)).thenReturn(List.of(new Client(), new Client()));
		
		// Service call
		List<Client> clients = clientService.findAllPassive(Locale.US);
		
		// Assertions
		assertEquals(2, clients.size());
		
		// Verifications
		verify(clientRepository, times(1)).findAllByDeletedAndActive(false, false);
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
		verify(clientRepository, times(2)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void findByIdTest_NotExistingClient_ShouldCauseClientNotFoundException() {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(clientRepository.findByIdAndDeletedAndActive(id, false, true)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(ClientNotFoundException.class, () -> clientService.findById(id, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).findByIdAndDeletedAndActive(id, false, true);
	}
	
	@Test
	public void createTest_NotExistingIdentificationNumberAndNotExistingPhoneNumber_ShouldNotCauseException() {
		// Create a client
		Client client = new Client("Identification Number", "Firstname", "Lastname", "Phone");
		
		// Mock the repository
		when(clientRepository.existsByIdentificationNumberAndDeleted(any(), anyBoolean())).thenReturn(false);
		when(clientRepository.existsByPhoneAndDeleted(any(), anyBoolean())).thenReturn(false);
		when(clientRepository.save(any())).thenReturn(client);
		
		// Assertions
		assertDoesNotThrow(() -> clientService.create(client, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).existsByIdentificationNumberAndDeleted(client.getIdentificationNumber(), false);
		verify(clientRepository, times(1)).existsByPhoneAndDeleted(client.getPhone(), false);
		verify(clientRepository, times(1)).save(client);
	}
	
	@Test
	public void createTest_ExistingIdentificationNumberAndNotExistingPhoneNumber_ShouldCauseIdentificationNumberAlreadyUsedException() {
		// Create a client
		Client client = new Client("Identification Number", "Firstname", "Lastname", "Phone");
		
		// Mock the repository
		when(clientRepository.existsByIdentificationNumberAndDeleted(any(), anyBoolean())).thenReturn(true);
		
		// Assertions
		assertThrows(IdentificationNumberAlreadyUsedException.class, () -> clientService.create(client, Locale.US));
		// Verifications
		verify(clientRepository, times(1)).existsByIdentificationNumberAndDeleted(client.getIdentificationNumber(), false);
		verify(clientRepository, times(0)).existsByPhoneAndDeleted(client.getPhone(), false);
		verify(clientRepository, times(0)).save(client);
	}
	
	@Test
	public void createTest_NotExistingIdentificationNumberAndExistingPhoneNumber_ShouldCausePhoneNumberAlreadyUsedException() {
		// Create a client
		Client client = new Client("Identification Number", "Firstname", "Lastname", "Phone");
		
		// Mock the repository
		when(clientRepository.existsByIdentificationNumberAndDeleted(any(), anyBoolean())).thenReturn(false);
		when(clientRepository.existsByPhoneAndDeleted(any(), anyBoolean())).thenReturn(true);
		
		// Assertions
		assertThrows(PhoneNumberAlreadyUsedException.class, () -> clientService.create(client, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).existsByIdentificationNumberAndDeleted(client.getIdentificationNumber(), false);
		verify(clientRepository, times(1)).existsByPhoneAndDeleted(client.getPhone(), false);
		verify(clientRepository, times(0)).save(client);
	}
	
	@Test
	public void updateTest_NoException_ShouldNotCauseException() {
		// Mock the repository
		when(clientRepository.existsByIdAndDeleted(any(), anyBoolean())).thenReturn(true);
		when(clientRepository.existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(false);
		when(clientRepository.existsByPhoneAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(false);
		
		// Assertions
		assertDoesNotThrow(() -> clientService.update(new Client(), Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).existsByIdAndDeleted(any(), anyBoolean());
		verify(clientRepository, times(1)).existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(1)).existsByPhoneAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(1)).save(new Client());
	}
	
	@Test
	public void updateTest_NotExistingClient_ShouldCauseClientNotFoundException() {
		// Mock the repository
		when(clientRepository.existsByIdAndDeleted(any(), anyBoolean())).thenReturn(false);
		
		// Assertions
		assertThrows(ClientNotFoundException.class, () -> clientService.update(new Client(), Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).existsByIdAndDeleted(any(), anyBoolean());
		verify(clientRepository, times(0)).existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(0)).existsByPhoneAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(0)).save(new Client());
	}
	
	@Test
	public void updateTest_AlreadyUsedIdentificationNumber_ShouldCauseIdentificationNumberAlreadyUsedException() {
		// Mock the repository
		when(clientRepository.existsByIdAndDeleted(any(), anyBoolean())).thenReturn(true);
		when(clientRepository.existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(true);
		
		// Assertions
		assertThrows(IdentificationNumberAlreadyUsedException.class, () -> clientService.update(new Client(), Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).existsByIdAndDeleted(any(), anyBoolean());
		verify(clientRepository, times(1)).existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(0)).existsByPhoneAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(0)).save(new Client());
	}
	
	@Test
	public void updateTest_AlreadyUsedPhoneNumber_ShouldCausePhoneNumberAlreadyUsedException() {
		// Mock the repository
		when(clientRepository.existsByIdAndDeleted(any(), anyBoolean())).thenReturn(true);
		when(clientRepository.existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(false);
		when(clientRepository.existsByPhoneAndDeletedAndIdNot(any(), anyBoolean(), any())).thenReturn(true);
		
		// Assertions
		assertThrows(PhoneNumberAlreadyUsedException.class, () -> clientService.update(new Client(), Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).existsByIdAndDeleted(any(), anyBoolean());
		verify(clientRepository, times(1)).existsByIdentificationNumberAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(1)).existsByPhoneAndDeletedAndIdNot(any(), anyBoolean(), any());
		verify(clientRepository, times(0)).save(new Client());
	}
	
	@Test
	public void deleteTest_ExistingClient_ShouldNotCauseException() {
		// Create an id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(clientRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.of(new Client()));
		
		// Assertions
		assertDoesNotThrow(() -> clientService.delete(id, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).findByIdAndDeleted(id, false);
	}
	
	@Test
	public void deleteTest_NotExistingClient_ShouldCauseClientNotFoundException() {
		// Create an id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(clientRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(ClientNotFoundException.class, () -> clientService.delete(id, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).findByIdAndDeleted(id, false);
	}
	
	@Test
	public void getUser_ExistingClient_ShouldNotCauseException() {
		// Create an id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(clientRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.of(new Client()));
		
		// Assertions
		assertDoesNotThrow(() -> clientService.getUser(id, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).findByIdAndDeleted(id, false);
		
	}
	
	@Test
	public void getUser_NotExistingClient_ShouldCauseClientNotFoundException() {
		// Create an id
		UUID id = UUID.randomUUID();
		
		// Mock the repository
		when(clientRepository.findByIdAndDeleted(id, false)).thenReturn(Optional.empty());
		
		// Assertions
		assertThrows(ClientNotFoundException.class, () -> clientService.getUser(id, Locale.US));
		
		// Verifications
		verify(clientRepository, times(1)).findByIdAndDeleted(id, false);
		
	}
}
