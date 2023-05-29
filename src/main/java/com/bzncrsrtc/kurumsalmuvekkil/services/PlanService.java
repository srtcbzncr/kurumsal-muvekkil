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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PlanNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
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
		return planRepository.findAllByDeletedAndActive(false, true);
	}
	
	public Plan findById(UUID id, Locale locale) {
		Optional<Plan> plan = planRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(plan.isEmpty()) {
			throw new PlanNotFoundException(messageSource.getMessage("plan.not.found.message", null, locale));
		}
		
		return plan.get();
	}
	
	public List<Plan> findAllDeleted(Locale locale){
		return planRepository.findAllByDeleted(true);
	}
	
	public List<Plan> findAllPassive(Locale locale){
		return planRepository.findAllByDeletedAndActive(false, false);
	}
	
	public Plan create(Plan plan, Locale locale) {
		Plan savedPlan = planRepository.save(plan);
		return savedPlan;
	}
	
	public void update(Plan plan, Locale locale) {
		if(!planRepository.existsByIdAndDeleted(plan.getId(), false)) {
			throw new PlanNotFoundException(messageSource.getMessage("plan.not.found.message", null, locale));
		}
		
		planRepository.save(plan);
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
	
	public List<Subscription> getSubscriptions(UUID planId, Locale locale){
		Optional<Plan> plan = planRepository.findByIdAndDeletedAndActive(planId, false, true);
		
		if(plan.isEmpty()) {
			throw new PlanNotFoundException(messageSource.getMessage("plan.not.found.message", null, locale));
		}
		
		List<Subscription> subscriptions = plan.get().getSubscriptions();
		return subscriptions;
	}
	
}
