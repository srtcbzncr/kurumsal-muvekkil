package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;

public interface LawyerRepository extends JpaRepository<Lawyer, UUID> {
	Optional<Lawyer> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Lawyer> findByIdAndActive(UUID id, boolean active);
	Optional<Lawyer> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	
	Optional<Lawyer> findByUserId(UUID userId);
	Optional<Lawyer> findByUserIdAndDeleted(UUID userId, boolean deleted);
	Optional<Lawyer> findByUserIdAndDeletedAndActive(UUID userId, boolean deleted, boolean active);
	
	List<Lawyer> findAllByDeleted(boolean deleted);
	List<Lawyer> findAllByActive(boolean active);
	List<Lawyer> findAllByDeletedAndActive(boolean deleted, boolean active);
	
	List<Lawyer> findAllByCompanyId(UUID companyId);
	List<Lawyer> findAllByCompanyIdAndDeleted(UUID companyId, boolean deleted);
	List<Lawyer> findAllByCompanyIdAndDeletedAndActive(UUID companyId, boolean deleted, boolean active);
	
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
	boolean existsByIdentificationNumberAndDeleted(String identificationNumber, boolean deleted);
	boolean existsByPhoneAndDeleted(String phone, boolean deleted);
	boolean existsByIdentificationNumberAndDeletedAndIdNot(String identificationNumber, boolean deleted, UUID id);
	boolean existsByPhoneAndDeletedAndIdNot(String phone, boolean deleted, UUID id);
	
	int countByDeleted(boolean deleted);
	int countByDeletedAndActive(boolean deleted, boolean active);
}
