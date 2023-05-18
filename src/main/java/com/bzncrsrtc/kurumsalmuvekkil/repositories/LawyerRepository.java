package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;

public interface LawyerRepository extends JpaRepository<Lawyer, UUID> {
	Optional<Lawyer> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Lawyer> findAllByDeletedAndActive(boolean deleted, boolean active);
}
