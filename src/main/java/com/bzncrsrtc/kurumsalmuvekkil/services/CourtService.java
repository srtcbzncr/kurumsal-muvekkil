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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CourtRepository;

@Service
public class CourtService {

	private final CourtRepository courtRepository;
	private final MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(CourtService.class);
	
	public CourtService(CourtRepository courtRepository, MessageSource messageSource) {
		this.courtRepository = courtRepository;
		this.messageSource = messageSource;
	}
	
	public List<Court> findAll(Locale locale){
		return courtRepository.findAllByDeletedAndActive(false, true);
	}
	
	public Court findById(UUID id, Locale locale) {
		Optional<Court> court = courtRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(court.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return court.get();
	}
	
	public List<Court> findAllDeleted(Locale locale){
		return courtRepository.findAllByDeleted(true);
	}
	
	public List<Court> findAllPassive(Locale locale){
		return courtRepository.findAllByDeletedAndActive(false, false);
	}
	
	public Court create(Court court, Locale locale) {
		if(courtRepository.existsByNameAndDeleted(court.getName(), false)) {
			throw new CourtExistsException(messageSource.getMessage("court.exists.message", null, locale));
		}

		Court savedCourt = courtRepository.save(court);
		return savedCourt;
	}
	
	public Court add(UUID parentId, Court court, Locale locale) {
		Optional<Court> parent = courtRepository.findByIdAndDeletedAndActive(parentId, false, true);
		
		if(parent.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("parent.court.not.found.message", null, locale));
		}
		
		if(courtRepository.existsByNameAndDeleted(court.getName(), false)) {
			throw new CourtExistsException(messageSource.getMessage("court.exists.message", null, locale));
		}
		
		court.setParent(parent.get());
		Court savedCourt = courtRepository.save(court);
		return savedCourt;
	}
	
	public void update(Court court, Locale locale) {
		if(!courtRepository.existsByIdAndDeleted(court.getId(), false)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		courtRepository.save(court);
	}
	
	public void updateParent(UUID parentId, Court court, Locale locale) {
		
		Optional<Court> parent = courtRepository.findByIdAndDeletedAndActive(parentId, false, true);
		
		if(parent.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("parent.court.not.found.message", null, locale));
		}
		
		if(!courtRepository.existsByIdAndDeleted(court.getId(), false)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		court.setParent(parent.get());
		courtRepository.save(court);		
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Court> optionalCourt = courtRepository.findByIdAndDeleted(id, false);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		Court court = optionalCourt.get();
		court.setDeleted(true);
		court.setDeletedAt(LocalDateTime.now());
		courtRepository.save(court);
	}
	
	public Court getParent(UUID id, Locale locale) {
		Optional<Court> optionalCourt = courtRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		Court parent = optionalCourt.get().getParent();
		
		return parent;
	}
	
	public List<Court> getSubs(UUID id, Locale locale){
		Optional<Court> optionalCourt = courtRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		List<Court> subs = optionalCourt.get().getSubs();
		return subs;
	}
	
	public List<File> getFiles(UUID id, Locale locale){
		Optional<Court> optionalCourt = courtRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		List<File> files = optionalCourt.get().getFiles();
		return files;
	}
	
}
