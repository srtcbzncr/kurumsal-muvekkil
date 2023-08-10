package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Role;
import com.bzncrsrtc.kurumsalmuvekkil.models.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	Optional<Role> findByName(String name);
	
	UUID findIdByName(String name);
	
	boolean existsByName(String name);
}
