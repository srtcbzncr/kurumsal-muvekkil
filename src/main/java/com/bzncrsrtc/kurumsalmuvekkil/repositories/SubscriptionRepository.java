package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
	Optional<Subscription> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Subscription> findByIdAndActive(UUID id, boolean active);
	Optional<Subscription> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Subscription> findAllByDeletedAndActive(boolean deleted, boolean active);
	List<Subscription> findAllByDeleted(boolean deleted);
	List<Subscription> findAllByActive(boolean active);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
	boolean existsByCompanyIdAndDeleted(UUID companyId, boolean deleted);
}
