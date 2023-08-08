package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
	Optional<Plan> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Plan> findByIdAndActive(UUID id, boolean active);
	Optional<Plan> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	
	List<Plan> findAllByDeletedAndActive(boolean deleted, boolean active);
	List<Plan> findAllByDeleted(boolean deleted);
	List<Plan> findAllByActive(boolean active);
	
	int countByDeleted(boolean deleted);
	int countByActiveAndDeleted(boolean active, boolean deleted);
	
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
}
