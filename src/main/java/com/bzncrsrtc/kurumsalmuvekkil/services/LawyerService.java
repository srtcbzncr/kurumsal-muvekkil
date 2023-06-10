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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.IdentificationNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.LawyerNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PhoneNumberAlreadyUsedException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.LawyerRepository;

@Service
public class LawyerService {

	private LawyerRepository lawyerRepository;
	private MessageSource messageSource;
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(LawyerService.class);
	
	public LawyerService(LawyerRepository lawyerRepository, MessageSource messageSource, UserService userService) {
		this.lawyerRepository = lawyerRepository;
		this.messageSource = messageSource;
		this.userService = userService;
	}
	
	public List<Lawyer> findAll(Locale locale){
		return lawyerRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Lawyer> findAllDeleted(Locale locale){
		return lawyerRepository.findAllByDeleted(true);
	}
	
	public List<Lawyer> findAllPassive(Locale locale){
		return lawyerRepository.findAllByDeletedAndActive(false, false);
	}
	
	public Lawyer findById(UUID id, Locale locale) {
		Optional<Lawyer> lawyer = lawyerRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(lawyer.isEmpty()) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
		
		return lawyer.get();
	}
	
	@Transactional
	public Lawyer create(Lawyer lawyer, Locale locale) {
		if(lawyerRepository.existsByIdentificationNumberAndDeleted(lawyer.getIdentificationNumber(), false)) {
			throw new IdentificationNumberAlreadyUsedException(messageSource.getMessage("identification.number.already.used.message", null, locale));
		}
		
		if(lawyerRepository.existsByPhoneAndDeleted(lawyer.getPhone(), false)) {
			throw new PhoneNumberAlreadyUsedException(messageSource.getMessage("phone.number.already.used.message", null, locale));
		}
		
		lawyer.setUser(userService.create(lawyer.getUser(), locale));
		
		return lawyerRepository.save(lawyer);
	}
	
	@Transactional
	public void update(Lawyer lawyer, Locale locale) {
		if(!lawyerRepository.existsByIdAndDeleted(lawyer.getId(), false)) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
		
		if(lawyerRepository.existsByIdentificationNumberAndDeletedAndIdNot(lawyer.getIdentificationNumber(), false, lawyer.getId())) {
			throw new IdentificationNumberAlreadyUsedException(messageSource.getMessage("identification.number.already.used.message", null, locale));
		}
		
		if(lawyerRepository.existsByPhoneAndDeletedAndIdNot(lawyer.getPhone(), false, lawyer.getId())) {
			throw new PhoneNumberAlreadyUsedException(messageSource.getMessage("phone.number.already.used.message", null, locale));
		}
		
		userService.update(lawyer.getUser(), locale);
		lawyerRepository.save(lawyer);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Lawyer> optionalLawyer = lawyerRepository.findByIdAndDeleted(id, false);
		
		if(optionalLawyer.isEmpty()) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
		
		Lawyer lawyer = optionalLawyer.get();
		
		lawyer.setDeleted(true);
		lawyer.setDeletedAt(LocalDateTime.now());
		lawyerRepository.save(lawyer);
	}
	
}
