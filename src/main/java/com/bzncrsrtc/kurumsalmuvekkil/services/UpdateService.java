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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.UpdateNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Update;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UpdateRepository;

@Service
public class UpdateService {

	private UpdateRepository updateRepository;
	private MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(UpdateService.class);
	
	public UpdateService(UpdateRepository updateRepository, MessageSource messageSource) {
		this.updateRepository = updateRepository;
		this.messageSource = messageSource;
	}
	
	public List<Update> findAll(Locale locale){
		return updateRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Update> findAllDeleted(Locale locale){
		return updateRepository.findAllByDeleted(true);
	}
	
	public List<Update> findAllPassive(Locale locale){
		return updateRepository.findAllByDeletedAndActive(false, false);
	}
	
	public Update findById(UUID id, Locale locale) {
		Optional<Update> update = updateRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(update.isEmpty()) {
			throw new UpdateNotFoundException(messageSource.getMessage("update.not.found.message", null, locale));
		}
		
		return update.get();
	}
	
	public Update create(Update update, Locale locale) {
		Update savedUpdate = updateRepository.save(update);
		
		return savedUpdate;
	}
	
	public void update(Update update, Locale locale) {
		if(!updateRepository.existsByIdAndDeleted(update.getId(), false)) {
			throw new UpdateNotFoundException(messageSource.getMessage("update.not.found.message", null, locale));
		}
		
		updateRepository.save(update);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Update> optionalUpdate = updateRepository.findByIdAndDeleted(id, false);
		
		if(optionalUpdate.isEmpty()) {
			throw new UpdateNotFoundException(messageSource.getMessage("update.not.found.message", null, locale)); 
		}
		
		Update update = optionalUpdate.get();
		
		update.setDeleted(true);
		update.setDeletedAt(LocalDateTime.now());
	}
	
}
