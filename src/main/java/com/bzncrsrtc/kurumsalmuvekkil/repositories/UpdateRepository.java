package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Update;

public interface UpdateRepository extends JpaRepository<Update, UUID> {
	Optional<Update> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Update> findByIdAndActive(UUID id, boolean active);
	Optional<Update> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Update> findAllByDeletedAndActive(boolean deleted, boolean active);
	List<Update> findAllByDeleted(boolean deleted);
	List<Update> findAllByActive(boolean active);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
}
