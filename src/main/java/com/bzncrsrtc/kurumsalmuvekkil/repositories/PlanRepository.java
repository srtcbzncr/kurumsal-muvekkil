package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
	Optional<Plan> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Plan> findAllByDeletedAndActive(boolean deleted, boolean active);
}
