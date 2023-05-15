package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="role")
	private String role;
	
	@Column(name="is_new")
	private boolean isNew = true;

	@Column(name="deleted")
	private boolean deleted = false;
	
	@Column(name="active")
	private boolean active = true;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="deleted_at")
	private LocalDateTime deletedAt;

}
