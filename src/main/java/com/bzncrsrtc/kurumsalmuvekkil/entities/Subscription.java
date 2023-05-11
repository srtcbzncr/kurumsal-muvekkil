package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity(name="subscriptions")
public class Subscription {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@OneToOne()
	@JoinColumn(name="company_id", referencedColumnName="id")
	private Company company;
	
	@ManyToOne()
	@JoinColumn(name="plan_id", referencedColumnName="id")
	private Plan plan;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;

}
