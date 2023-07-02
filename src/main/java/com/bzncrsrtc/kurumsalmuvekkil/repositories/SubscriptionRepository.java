package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.enums.SubscriptionType;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
	Optional<Subscription> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Subscription> findByIdAndActive(UUID id, boolean active);
	Optional<Subscription> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	
	List<Subscription> findAllByDeletedAndActive(boolean deleted, boolean active);
	List<Subscription> findAllByDeleted(boolean deleted);
	List<Subscription> findAllByActive(boolean active);
	
	List<Subscription> findAllByCompanyId(UUID companyId);
	List<Subscription> findAllByCompanyIdAndDeleted(UUID companyId, boolean deleted);
	List<Subscription> findAllByCompanyIdAndDeletedAndActive(UUID companyId, boolean deleted, boolean active);
	
	List<Subscription> findAllByPlanId(UUID planId);
	List<Subscription> findAllByPlanIdAndDeleted(UUID planId, boolean deleted);
	List<Subscription> findAllByPlanIdAndDeletedAndActive(UUID planId, boolean deleted, boolean active);
	
	List<Subscription> findAllByType(SubscriptionType type);
	List<Subscription> findAllByTypeAndDeleted(SubscriptionType type, boolean deleted);
	List<Subscription> findAllByTypeAndDeletedAndActive(SubscriptionType type, boolean deleted, boolean active);
	
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
	boolean existsByCompanyIdAndDeleted(UUID companyId, boolean deleted);
}
