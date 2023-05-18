package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Court;

public interface CourtRepository extends JpaRepository<Court, UUID> {
	Optional<Court> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Court> findAllByDeletedAndActive(boolean deleted, boolean active);
}
