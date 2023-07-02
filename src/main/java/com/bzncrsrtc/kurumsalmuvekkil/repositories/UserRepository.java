package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.User;

public interface UserRepository extends JpaRepository<User, UUID>{
	Optional<User> findByIdAndDeleted(UUID id, boolean deleted);
	Optional<User> findByIdAndActive(UUID id, boolean active);
	Optional<User> findByIdAndDeletedAndActive(UUID id, boolean deleted, boolean active);
	Optional<User> findByEmailAndDeletedAndActive(String email, boolean deleted, boolean active);
	Optional<User> findByUsernameAndDeletedAndActive(String username, boolean deleted, boolean active);
	
	List<User> findAllByDeleted(boolean deleted);
	List<User> findAllByActive(boolean active);
	List<User> findAllByDeletedAndActive(boolean deleted, boolean active);
	
	List<User> findAllByRoleId(UUID roleId);
	List<User> findAllByRoleIdAndDeleted(UUID roleId, boolean deleted);
	List<User> findAllByRoleIdAndDeletedAndActive(UUID roleId, boolean deleted, boolean active);
	
	boolean existsByUsernameAndDeleted(String username, boolean deleted);
	boolean existsByEmailAndDeleted(String email, boolean deleted);
	boolean existsByIdAndDeleted(UUID id, boolean deleted);	
	boolean existsByUsernameAndDeletedAndIdNot(String username, boolean deleted, UUID id);
	boolean existsByEmailAndDeletedAndIdNot(String email, boolean deleted, UUID id);
}
