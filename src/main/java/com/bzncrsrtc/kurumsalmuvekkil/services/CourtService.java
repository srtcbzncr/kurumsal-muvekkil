package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
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
		return courtRepository.findAllByDeleted(false);
	}
	
	public List<Court> findAllActive(Locale locale){
		return courtRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Court> findAllPassive(Locale locale){
		return courtRepository.findAllByDeletedAndActive(false, false);
	}
	
	public List<Court> findAllDeleted(Locale locale){
		return courtRepository.findAllByDeleted(true);
	}
	
	public List<Court> findAllByParentId(UUID id, Locale locale){
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.findAllByParentIdAndDeleted(id, false);
	}
	
	public List<Court> findAllActiveByParentId(UUID id, Locale locale){
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			
			if(!courtRepository.existsById(id)) {
				throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
			}
			
		}
		else {
			if(!courtRepository.existsByIdAndActiveAndDeleted(id, true, false)) {
				throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
			}
		}
		
		return courtRepository.findAllByParentIdAndDeletedAndActive(id, false, true);
	}
	
	public List<Court> findAllPassiveByParentId(UUID id, Locale locale){
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.findAllByParentIdAndDeletedAndActive(id, false, false);
	}
	
	public List<Court> findAllDeletedByParentId(UUID id, Locale locale){
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.findAllByParentIdAndDeleted(id, true);
	}
	 
	public Court findById(UUID id, Locale locale) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Court> court;
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			court = courtRepository.findById(id);
		}
		else {
			court = courtRepository.findByIdAndDeletedAndActive(id, false, true);	
		}
	
		if(court.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return court.get();
		
	}
	
	public Court create(Court court, Locale locale) {
		if(courtRepository.existsByNameAndDeleted(court.getName(), false)) {
			throw new CourtExistsException(messageSource.getMessage("court.exists.message", null, locale));
		}
		
		if(court.getParent() != null && !courtRepository.existsByIdAndDeleted(court.getParent().getId(), false)) {
			throw new CourtNotFoundException(messageSource.getMessage("parent.court.not.found.message", null, locale));
		}

		Court savedCourt = courtRepository.save(court);
		return savedCourt;
	}
	
	public Court update(Court court, Locale locale) {
		if(!courtRepository.existsByIdAndDeleted(court.getId(), false)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		if(courtRepository.existsByIdNotAndNameAndDeleted(court.getId(), court.getName(), false)) {
			throw new CourtExistsException(messageSource.getMessage("court.exists.message", null, locale));
		}
		
		if(court.getParent() != null && !courtRepository.existsByIdAndDeleted(court.getParent().getId(), false)) {
			throw new CourtNotFoundException(messageSource.getMessage("parent.court.not.found.message", null, locale));
		}
		
		return courtRepository.save(court);
	}
	
	public Court setActive(UUID id, Locale locale) {
		Optional<Court> optionalCourt = courtRepository.findById(id);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		Court court = optionalCourt.get();
		court.setActive(true);
		
		return courtRepository.save(court);
	}
	
	@Transactional
	public Court setPassive(UUID id, Locale locale) {
		Optional<Court> optionalCourt = courtRepository.findById(id);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		this.setPassiveSubs(id, locale);
		
		Court court = optionalCourt.get();
		court.setActive(false);
		
		return courtRepository.save(court);
	}
	
	public Court updateParent(UUID parentId, Court court, Locale locale) {
		
		Optional<Court> parent = courtRepository.findByIdAndDeletedAndActive(parentId, false, true);
		
		if(parent.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("parent.court.not.found.message", null, locale));
		}
		
		if(!courtRepository.existsByIdAndDeleted(court.getId(), false)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		court.setParent(parent.get());
		
		return courtRepository.save(court);		
	}
	
	@Transactional
	public void delete(UUID id, Locale locale) {
		Optional<Court> optionalCourt = courtRepository.findByIdAndDeleted(id, false);
		
		if(optionalCourt.isEmpty()) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		this.deleteSubs(id, locale);
		
		Court court = optionalCourt.get();
		
		court.setDeleted(true);
		court.setDeletedAt(LocalDateTime.now());
		courtRepository.save(court);
	}
	
	@Transactional
	public void setPassiveSubs(UUID id, Locale locale) {
		if(!courtRepository.existsByIdAndDeleted(id, false)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		List<Court> subCourts = courtRepository.findAllByParentIdAndDeletedAndActive(id, false, true);
		subCourts.forEach((court) -> {
			court.setActive(false);
			courtRepository.save(court);
		});
	}
	
	@Transactional
	public void deleteSubs(UUID id, Locale locale) {		
		if(!courtRepository.existsByIdAndDeleted(id, false)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		List<Court> subCourts = courtRepository.findAllByParentIdAndDeleted(id, false);
		subCourts.forEach((court) -> {
			court.setDeleted(true);
			court.setDeletedAt(LocalDateTime.now());
			courtRepository.save(court);
		});
	}
	
	public int allCount(Locale locale) {
		return courtRepository.countByDeleted(false);
	}
	
	public int allCountByParentId(UUID id, Locale locale) {
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.countByParentIdAndDeleted(id, false);
	}
	
	public int activeCount(Locale locale) {
		return courtRepository.countByActiveAndDeleted(true, false);
	}
	
	public int activeCountByParentId(UUID id, Locale locale) {
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.countByParentIdAndActiveAndDeleted(id, true, false);
	}
	
	public int passiveCount(Locale locale) {
		return courtRepository.countByActiveAndDeleted(false, false);
	}
	
	public int passiveCountByParentId(UUID id, Locale locale) {
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.countByParentIdAndActiveAndDeleted(id, false, false);
	}
	
	public int deletedCount(Locale locale) {
		return courtRepository.countByDeleted(true);
	}
	
	public int deletedCountByParentId(UUID id, Locale locale) {
		
		if(!courtRepository.existsById(id)) {
			throw new CourtNotFoundException(messageSource.getMessage("court.not.found.message", null, locale));
		}
		
		return courtRepository.countByParentIdAndDeleted(id, true);
	}
}
