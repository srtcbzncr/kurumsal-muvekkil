package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name="companies")
public class Company {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(name="name")
	private String name;
	
	@OneToOne(mappedBy="company", cascade=CascadeType.ALL)
	private Subscription subscription;
	
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL)
	private List<Lawyer> lawyers;
	
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL)
	private List<File> files; 
	
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
