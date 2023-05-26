package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{
	List<Company> findAllByDeleted(boolean deleted);
	List<Company> findAllByActive(boolean active);
	List<Company> findAllByDeletedAndActive(boolean deleted, boolean active);
	Optional<Company> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Company> findByIdAndActive(UUID id, boolean active);
	Optional<Company> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	boolean existsByNameAndDeleted(String name, boolean deleted);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
}
