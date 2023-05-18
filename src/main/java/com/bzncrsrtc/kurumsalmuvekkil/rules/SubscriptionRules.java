package com.bzncrsrtc.kurumsalmuvekkil.rules;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.SubscriptionNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ThereIsNoSubscriptionException;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.SubscriptionRepository;

@Component
public class SubscriptionRules {

	private final SubscriptionRepository subscriptionRepository;
	private final MessageSource messageSource;
	
	public SubscriptionRules(SubscriptionRepository subscriptionRepository, MessageSource messageSource) {
		this.subscriptionRepository = subscriptionRepository;
		this.messageSource = messageSource;
	}

	public void isNull(Optional<Subscription> subscription, Locale locale) {
		if(subscription.isEmpty()) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
	}
	
	public void isNull(Subscription subscription, Locale locale) {
		if(subscription == null) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
	}
	
	public void isNull(List<Subscription> subscriptions, Locale locale) {
		if(subscriptions.size() == 0) {
			throw new ThereIsNoSubscriptionException(messageSource.getMessage("there.is.no.subscription.message", null, locale));
		}
	}

	public void isThere(UUID id, Locale locale) {
		if(id == null || subscriptionRepository.existsById(id)) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
	}
	
	public void isDeleted(Subscription subscription, Locale locale) {
		if(subscription.isDeleted()) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
	}
	
	public void isPassive(Subscription subscription, Locale locale) {
		if(!subscription.isActive()) {
			throw new SubscriptionNotFoundException(messageSource.getMessage("subscription.not.found.message", null, locale));
		}
	}
	
}
