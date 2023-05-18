package com.bzncrsrtc.kurumsalmuvekkil.rules;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.LawyerNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ThereIsNoLawyerException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.LawyerRepository;

@Component
public class LawyerRules {

	private final LawyerRepository lawyerRepository;
	private final MessageSource messageSource;
	
	public LawyerRules(LawyerRepository lawyerRepository, MessageSource messageSource) {
		this.lawyerRepository = lawyerRepository;
		this.messageSource = messageSource;
	}

	public void isNull(Optional<Lawyer> lawyer, Locale locale) {
		if(lawyer.isEmpty()) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
	}
	
	public void isNull(Lawyer lawyer, Locale locale) {
		if(lawyer == null) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
	}
	
	public void isNull(List<Lawyer> lawyers, Locale locale) {
		if(lawyers.size() == 0) {
			throw new ThereIsNoLawyerException(messageSource.getMessage("there.is.no.lawyer.message", null, locale));
		}
	}

	public void isThere(UUID id, Locale locale) {
		if(id == null || lawyerRepository.existsById(id)) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
	}
	
	public void isDeleted(Lawyer lawyer, Locale locale) {
		if(lawyer.isDeleted()) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
	}
	
	public void isPassive(Lawyer lawyer, Locale locale) {
		if(!lawyer.isActive()) {
			throw new LawyerNotFoundException(messageSource.getMessage("lawyer.not.found.message", null, locale));
		}
	}
	
}
