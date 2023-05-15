package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name="plans")
public class Plan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="monthly_price")
	private double monthlyPrice;
	
	@Column(name="annual_price")
	private double annualPrice;
	
	@Column(name="client_quota")
	private int clientQuota;
	
	@Column(name="lawyer_quota")
	private int lawyerQuota;
	
	@Column(name="file_quota_per_client")
	private int fileQuotaPerClient;
	
	@OneToMany(mappedBy="plan")
	private List<Subscription> subscriptions;
	
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
