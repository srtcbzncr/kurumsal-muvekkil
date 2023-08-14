package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateClientRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateClientRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ClientResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ClientStatisticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.FileResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.ClientService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

	private ClientService clientService;
	private ResponseMapper responseMapper;
	private RequestMapper requestMapper;
	
	public ClientController(ClientService clientService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.clientService = clientService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> stats(@RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCount = clientService.allCount(locale);
		int activeCount = clientService.activeCount(locale);
		int passiveCount = clientService.passiveCount(locale);
		int deletedCount = clientService.deletedCount(locale);
		
		ClientStatisticsResponse response = new ClientStatisticsResponse(allCount, activeCount, passiveCount, deletedCount);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Client> clients = clientService.findAll(locale);
		List<ClientResponse> response = responseMapper.getClientListResponse(clients);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/active")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllActive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Client> clients = clientService.findAllActive(locale);
		List<ClientResponse> response = responseMapper.getClientListResponse(clients);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllPassive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Client> clients = clientService.findAllPassive(locale);
		List<ClientResponse> response = responseMapper.getClientListResponse(clients);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllDeleted(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Client> clients = clientService.findAllDeleted(locale);
		List<ClientResponse> response = responseMapper.getClientListResponse(clients);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Client client = clientService.findById(id, locale);
		ClientResponse response = responseMapper.getClientResponse(client);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	public ResponseEntity<Object> create(@Valid @RequestBody CreateClientRequest createClientRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Client client = requestMapper.fromCreateClientRequestToClient(createClientRequest);
		
		Client savedClient = clientService.create(client, locale);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedClient.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}
	
	@PutMapping("/{id}/setActive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setActive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Client client = clientService.setActive(id, locale);
		ClientResponse response = responseMapper.getClientResponse(client);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);		
	}
	
	@PutMapping("/{id}/setPassive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setPassive(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr) {
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Client client = clientService.setPassive(id, locale);
		ClientResponse response = responseMapper.getClientResponse(client);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);		
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateClientRequest updateClientRequest, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Client client = requestMapper.fromUpdateClientRequestToClient(updateClientRequest);
		clientService.update(client, locale);
		
		return ResponseHandler.generateResponse(client, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		clientService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/user")
	public ResponseEntity<Object> getUser(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		User user = clientService.getUser(id, locale);
		UserResponse response = responseMapper.getUserResponse(user);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
}
