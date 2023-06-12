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

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.AlreadyHasSubscriptionException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.SubscriptionNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {

	private SubscriptionRepository subscriptionRepository;
	private MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
	
	public SubscriptionService(SubscriptionRepository subscriptionRepository, MessageSource messageSource) {
		this.subscriptionRepository = subscriptionRepository;
		this.messageSource = messageSource;
	}
	
	public List<Subscription> findAll(Locale locale){
		return subscriptionRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<Subscription> findAllDeleted(Locale locale){
		return subscriptionRepository.findAllByDeleted(true);
	}
	
	public List<Subscription> findAllPassive(Locale locale){
		return subscriptionRepository.findAllByDeletedAndActive(false, true);
	}
	
	public Subscription findById(UUID id, Locale locale) {
		Optional<Subscription> subscription = subscriptionRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(subscription.isEmpty()) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
		
		return subscription.get();
	}
	
	public Subscription create(Subscription subscription, Locale locale) {
		if(subscriptionRepository.existsByCompanyIdAndDeleted(subscription.getCompany().getId(), false)) {
			throw new AlreadyHasSubscriptionException(messageSource.getMessage("already.has.subscription.message", null, locale));
		}
		
		Subscription savedSubscription = subscriptionRepository.save(subscription);
		return savedSubscription;
	}
	
	public void update(Subscription subscription, Locale locale) {
		if(!subscriptionRepository.existsByIdAndDeleted(subscription.getId(), false)) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
		
		subscriptionRepository.save(subscription);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<Subscription> optionalSubscription = subscriptionRepository.findByIdAndDeleted(id, false);
		
		if(optionalSubscription.isEmpty()) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
		
		Subscription subscription = optionalSubscription.get();
		subscription.setDeleted(true);
		subscription.setDeletedAt(LocalDateTime.now());
	}
	
}
