package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	Optional<Client> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	List<Client> findAllByDeletedAndActive(boolean deleted, boolean active);
}
