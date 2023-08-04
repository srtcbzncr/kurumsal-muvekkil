package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PlanNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.PlanRepository;

@Service
public class PlanService {

	private final PlanRepository planRepository;
	private final MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(PlanService.class);
	
	public PlanService(PlanRepository planRepository, MessageSource messageSource) {
		this.planRepository = planRepository;
		this.messageSource = messageSource;
	}
	
	public List<Plan> findAll(Locale locale){
		return planRepository.findAllByDeleted(false);
	}
	
	public List<Plan> findAllActive(Locale locale){
		return planRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Plan> findAllPassive(Locale locale) {
		return planRepository.findAllByDeletedAndActive(false, false);
	}
	
	public List<Plan> findAllDeleted(Locale locale) {
		return planRepository.findAllByDeleted(true);
	}
	
	public Plan findById(UUID id, Locale locale) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Plan> plan;
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			plan = planRepository.findById(id);
		}
		else {
			plan = planRepository.findByIdAndDeletedAndActive(id, false, true);
		}
		
		if(plan.isEmpty()) {
			throw new PlanNotFoundException(messageSource.getMessage("plan.not.found.message", null, locale));
		}
		
		return plan.get();
	}
	
	public Plan create(Plan plan, Locale locale) {
		return planRepository.save(plan);
	}
	
	public Plan update(Plan plan, Locale locale) {
		if(!planRepository.existsByIdAndDeleted(plan.getId(), false)) {
			throw new PlanNotFoundException(messageSource.getMessage("plan.not.found.message", null, locale));
		}
		
		return planRepository.save(plan);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Plan> optionalPlan = planRepository.findByIdAndDeleted(id, false);
		
		if(optionalPlan.isEmpty()) {
			throw new PlanNotFoundException(messageSource.getMessage("plan.not.found.message", null, locale));
		}
		
		Plan plan = optionalPlan.get();
		plan.setDeleted(true);
		plan.setDeletedAt(LocalDateTime.now());
		planRepository.save(plan);
	}
}
