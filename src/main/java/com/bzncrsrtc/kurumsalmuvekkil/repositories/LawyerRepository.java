package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;

public interface LawyerRepository extends JpaRepository<Lawyer, UUID> {
	List<Lawyer> findAllByDeleted(boolean deleted);
	List<Lawyer> findAllByActive(boolean active);
	List<Lawyer> findAllByDeletedAndActive(boolean deleted, boolean active);
	Optional<Lawyer> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Lawyer> findByIdAndActive(UUID id, boolean active);
	Optional<Lawyer> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
	boolean existsByIdentificationNumberAndDeleted(String identificationNumber, boolean deleted);
	boolean existsByPhoneAndDeleted(String phone, boolean deleted);
	boolean existsByIdentificationNumberAndDeletedAndIdNot(String identificationNumber, boolean deleted, UUID id);
	boolean existsByPhoneAndDeletedAndIdNot(String phone, boolean deleted, UUID id);
}
