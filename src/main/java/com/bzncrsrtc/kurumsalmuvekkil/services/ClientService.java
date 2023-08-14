package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ClientNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.IdentificationNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PhoneNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.ClientRepository;

@Service
public class ClientService {
	
	private ClientRepository clientRepository;
	private MessageSource messageSource;
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(ClientService.class);
	
	public ClientService(ClientRepository clientRepository, MessageSource messageSource, UserService userService) {
		this.clientRepository = clientRepository;
		this.messageSource = messageSource;
		this.userService = userService;
	}
	
	public List<Client> findAll(Locale locale){
		return clientRepository.findAllByDeleted(false);
	}
	
	public List<Client> findAllActive(Locale locale) {
		return clientRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Client> findAllPassive(Locale locale){
		return clientRepository.findAllByDeletedAndActive(false, false);
	}
	
	public List<Client> findAllDeleted(Locale locale){
		return clientRepository.findAllByDeleted(true);
	}
	
	public Client findById(UUID id, Locale locale) {
		Optional<Client> client = clientRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(client.isEmpty()) {
			throw new ClientNotFoundException(messageSource.getMessage("client.not.found.message", null, locale));
		}
		
		return client.get();
	}
	
	@Transactional
	public Client create(Client client, Locale locale) {
		if(clientRepository.existsByIdentificationNumberAndDeleted(client.getIdentificationNumber(), false)) {
			throw new IdentificationNumberAlreadyUsedException(messageSource.getMessage("identification.number.already.used.message", null, locale));
		}
		
		if(clientRepository.existsByPhoneAndDeleted(client.getPhone(), false)) {
			throw new PhoneNumberAlreadyUsedException(messageSource.getMessage("phone.number.already.used.message", null, locale));
		}
		
		client.setUser(userService.create(client.getUser(), locale));
		
		return clientRepository.save(client);
	}
	
	@Transactional
	public Client setActive(UUID id, Locale locale) {
		Optional<Client> optionalClient = clientRepository.findByIdAndDeleted(id, false);
		
		if(optionalClient.isEmpty()) {
			throw new ClientNotFoundException(messageSource.getMessage("client.not.found.message", null, locale));
		}
		
		Client client = optionalClient.get();
		
		userService.setActive(client.getUser().getId(), locale);
		
		client.setActive(true);
		
		return clientRepository.save(client);
	}
	
	@Transactional
	public Client setPassive(UUID id, Locale locale) {
		Optional<Client> optionalClient = clientRepository.findByIdAndDeleted(id, false);
		
		if(optionalClient.isEmpty()) {
			throw new ClientNotFoundException(messageSource.getMessage("client.not.found.message", null, locale));
		}
		
		Client client = optionalClient.get();
		
		userService.setPassive(client.getUser().getId(), locale);
		
		client.setActive(false);
		
		return clientRepository.save(client);
	}
 	
	@Transactional
	public void update(Client client, Locale locale) {
		if(!clientRepository.existsByIdAndDeleted(client.getId(), false)) {
			throw new ClientNotFoundException(messageSource.getMessage("client.not.found.message", null, locale));
		}
		
		if(clientRepository.existsByIdentificationNumberAndDeletedAndIdNot(client.getIdentificationNumber(), false, client.getId())) {
			throw new IdentificationNumberAlreadyUsedException(messageSource.getMessage("identification.number.already.used.message", null, locale));
		}
		
		if(clientRepository.existsByPhoneAndDeletedAndIdNot(client.getPhone(), false, client.getId())) {
			throw new PhoneNumberAlreadyUsedException(messageSource.getMessage("phone.number.already.used.message", null, locale)); 
		}
		
		userService.update(client.getUser(), locale);
		clientRepository.save(client);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Client> optionalClient = clientRepository.findByIdAndDeleted(id, false);
		
		if(optionalClient.isEmpty()) {
			throw new ClientNotFoundException(messageSource.getMessage("client.not.found.message", null, locale));
		}
		
		Client client = optionalClient.get();
		
		userService.delete(client.getUser().getId(), locale);
		
		client.setDeleted(true);
		client.setDeletedAt(LocalDateTime.now());
		
		clientRepository.save(client);
	}
	
	public User getUser(UUID id, Locale locale) {
		Optional<Client> client = clientRepository.findByIdAndDeleted(id, false);
		
		if(client.isEmpty()) {
			throw new ClientNotFoundException(messageSource.getMessage("client.not.found.message", null, locale));
		}
		
		return client.get().getUser();
	}
	
	public int allCount(Locale locale) {
		return clientRepository.countByDeleted(false);
	}
	
	public int activeCount(Locale locale) {
		return clientRepository.countByDeletedAndActive(false, true);
	}
	
	public int passiveCount(Locale locale) {
		return clientRepository.countByDeletedAndActive(false, false);
	}
	
	public int deletedCount(Locale locale) {
		return clientRepository.countByDeleted(true);
	}
}
