package com.bzncrsrtc.kurumsalmuvekkil.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name="companies")
public class Company {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@NonNull
	@Column(name="name", unique=true)
	private String name;
	
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL)
	private List<Subscription> subscriptions;
	
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL)
	private List<Lawyer> lawyers;
	
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL)
	private List<File> files; 
	
	@Column(name="deleted")
	private boolean deleted = false;
	
	@Column(name="active")
	private boolean active = true;
	
	@Column(name="created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@Column(name="deleted_at")
	private LocalDateTime deletedAt;
	
}
