package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	List<Client> findAllByDeleted(boolean deleted);
	List<Client> findAllByActive(boolean active);
	List<Client> findAllByDeletedAndActive(boolean deleted, boolean active);
	Optional<Client> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<Client> findByIdAndActive(UUID id, boolean active);
	Optional<Client> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	boolean existsByNameAndDeleted(String name, boolean deleted);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);
	boolean existsByIdentificationNumber(String identificationNumber);
	boolean existsByPhone(String phone);
}
