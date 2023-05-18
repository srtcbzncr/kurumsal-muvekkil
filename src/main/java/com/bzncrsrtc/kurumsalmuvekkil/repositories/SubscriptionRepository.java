package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
	Optional<Subscription> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Subscription> findAllByDeletedAndActive(boolean deleted, boolean active);
}
