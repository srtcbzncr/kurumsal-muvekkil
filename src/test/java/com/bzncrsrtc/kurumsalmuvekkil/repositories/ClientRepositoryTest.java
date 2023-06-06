package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;

class ClientRepositoryTest {

	@Autowired
	private ClientRepository clientRepository;
	
	Client passiveClient = new Client("Identification Number", "Firstname", "Lastname", "Phone");
	Client deletedClient = new Client("Identification Number", "Firstname", "Lastname", "Phone");
	Client deletedAndPassiveClient = new Client("Identification Number", "Firstname", "Lastname", "Phone");
	Client activeClient1 = new Client("Identification Number", "Firstname", "Lastname", "Phone");
	Client activeClient2 = new Client("Identification Number", "Firstname", "Lastname", "Phone");
	
	@BeforeEach
	public void init() {
		passiveClient.setActive(false);
		passiveClient = clientRepository.save(passiveClient);
		
		deletedClient.setDeleted(true);
		deletedClient.setDeletedAt(LocalDateTime.now());
		deletedClient = clientRepository.save(deletedClient);
		
		deletedAndPassiveClient.setActive(false);
		deletedAndPassiveClient.setDeleted(true);
		deletedAndPassiveClient.setDeletedAt(LocalDateTime.now());
		deletedAndPassiveClient = clientRepository.save(deletedAndPassiveClient);
		
		activeClient1 = clientRepository.save(activeClient1);
		activeClient2 = clientRepository.save(activeClient2);
	}

	@Test
	public void findAllTest() {
		List<Client> allCompanies = clientRepository.findAll();
		
		assertEquals(5, allCompanies.size());
	}	
	
	@Test
	public void findAllByDeletedAndActiveTest_findNotDeletedAndActiveCompanies() {
		List<Client> allCompanies = clientRepository.findAllByDeletedAndActive(false, true);
		
		assertEquals(2, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(true, client.isActive());
			assertEquals(false, client.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_findDeletedAndActiveCompanies() {
		List<Client> allCompanies = clientRepository.findAllByDeletedAndActive(true, true);
		
		assertEquals(1, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(true, client.isActive());
			assertEquals(true, client.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_findNotDeletedAndPassiveCompanies() {
		List<Client> allCompanies = clientRepository.findAllByDeletedAndActive(false, false);
		
		assertEquals(1, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(false, client.isActive());
			assertEquals(false, client.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedAndActiveTest_findDeletedAndPassiveCompanies() {
		List<Client> allCompanies = clientRepository.findAllByDeletedAndActive(true, false);
		
		assertEquals(1, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(false, client.isActive());
			assertEquals(true, client.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedTest_findNotDeletedCompanies() {
		List<Client> allCompanies = clientRepository.findAllByDeleted(false);
		
		assertEquals(3, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(false, client.isDeleted());
		}
	}
	
	@Test
	public void findAllByDeletedTest_findDeletedCompanies() {
		List<Client> allCompanies = clientRepository.findAllByDeleted(true);
		
		assertEquals(2, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(true, client.isDeleted());
		}
	}
	
	@Test
	public void findAllByActiveTest_findActiveCompanies() {
		List<Client> allCompanies = clientRepository.findAllByActive(true);
		
		assertEquals(3, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(true, client.isActive());
		}
	}
	
	@Test
	public void findAllByActiveTest_findPassiveCompanies() {
		List<Client> allCompanies = clientRepository.findAllByActive(false);
		
		assertEquals(2, allCompanies.size());
		for(Client client : allCompanies){
			assertEquals(false, client.isActive());
		}
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest_NotDeletedAndActiveClient() {
		Optional<Client> client = clientRepository.findByIdAndDeletedAndActive(activeClient1.getId(), false, true);
		
		assertEquals(false, client.isEmpty());
		assertEquals(true, client.get().isActive());
		assertEquals(false, client.get().isDeleted());
		assertEquals(client.hashCode(), activeClient1.hashCode());
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest_NotDeletedAndPassiveClient() {
		Optional<Client> client = clientRepository.findByIdAndDeletedAndActive(passiveClient.getId(), false, true);
		
		assertEquals(true, client.isEmpty());
	}
	
	@Test
	public void findByIdAndDeletedAndActiveTest_DeletedAndPassiveClient() {
		Optional<Client> client = clientRepository.findByIdAndDeletedAndActive(deletedClient.getId(), false, true);
		
		assertEquals(true, client.isEmpty());
	}
	
}
