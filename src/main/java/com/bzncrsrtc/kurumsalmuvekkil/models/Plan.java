package com.bzncrsrtc.kurumsalmuvekkil.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
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
	private BigDecimal monthlyPrice;
	
	@Column(name="annual_price")
	private BigDecimal annualPrice;
	
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
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@Column(name="deleted_at")
	private LocalDateTime deletedAt;

}
