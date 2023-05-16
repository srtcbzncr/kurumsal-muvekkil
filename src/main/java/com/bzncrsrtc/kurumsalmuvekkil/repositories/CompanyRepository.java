package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{
	Optional<Company> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Company> findAllByDeletedAndActive(boolean deleted, boolean active);
}
