package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.RoleAlreadyExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.RoleNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Role;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.RoleRepository;

@Service
public class RoleService {

	private RoleRepository roleRepository;
	private MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	public RoleService(RoleRepository roleRepository, MessageSource messageSource) {
		this.roleRepository = roleRepository;
		this.messageSource = messageSource;
	}
	
	public List<Role> findAll(Locale locale){
		return roleRepository.findAll();
	}
	
	public UUID findIdByName(String name, Locale locale) {
		Optional<Role> role = roleRepository.findByName(name);
		
		if(role.isEmpty()) {
			throw new RoleNotFoundException(messageSource.getMessage("role.not.found.message", null, locale));
		}
		
		return role.get().getId();
	}
	
	public Role findById(UUID id, Locale locale) {
		Optional<Role> role = roleRepository.findById(id);
		
		if(role.isEmpty()) {
			throw new RoleNotFoundException(messageSource.getMessage("role.not.found.message", null, locale));
		}
		
		return role.get();
	}
	
	public Role findByName(String name, Locale locale) {
		Optional<Role> role = roleRepository.findByName(name);
		
		if(role.isEmpty()) {
			throw new RoleNotFoundException(messageSource.getMessage("role.not.found.message", null, locale));
		}
		
		return role.get();
	}
	
	public Role create(Role role, Locale locale) {
		if(roleRepository.existsByName(role.getName())) {
			throw new RoleAlreadyExistsException(messageSource.getMessage("role.already.exists.message", null, locale));
		}
		
		return roleRepository.save(role);
	}
	
	public void update(Role role, Locale locale) {
		if(!roleRepository.existsByName(role.getName())) {
			throw new RoleNotFoundException(messageSource.getMessage("role.not.found.message", null, locale));
		}
		
		roleRepository.save(role);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Role> optionalRole = roleRepository.findById(id);
		
		if(optionalRole.isEmpty()) {
			throw new RoleNotFoundException(messageSource.getMessage("role.not.found.message", null, locale));
		}
		
		Role role = optionalRole.get();
		
		roleRepository.delete(role);
	}
	
}
