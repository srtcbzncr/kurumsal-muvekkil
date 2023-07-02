package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Court;

public interface CourtRepository extends JpaRepository<Court, UUID> {
	Optional<Court> findByIdAndActive(UUID id, boolean active);
	Optional<Court> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Court> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	
	List<Court> findAllByActive(boolean active);
	List<Court> findAllByDeleted(boolean deleted);
	List<Court> findAllByDeletedAndActive(boolean deleted, boolean active);
	
	List<Court> findAllByParentId(UUID parentId);
	List<Court> findAllByParentIdAndDeleted(UUID parentId, boolean deleted);
	List<Court> findAllByParentIdAndDeletedAndActive(UUID parentId, boolean deleted, boolean active);
	
	boolean existsByNameAndDeleted(String name, boolean deleted);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
}
