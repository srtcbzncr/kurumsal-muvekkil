package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.File;

public interface FileRepository extends JpaRepository<File, UUID> {
	Optional<File> findByIdAndActive(UUID id, boolean active);
	Optional<File> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<File> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<File> findAllByActive(boolean active);
	List<File> findAllByDeleted(boolean deleted);
	List<File> findAllByDeletedAndActive(boolean deleted, boolean active);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
}