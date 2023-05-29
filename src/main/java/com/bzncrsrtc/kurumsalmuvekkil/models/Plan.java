package com.bzncrsrtc.kurumsalmuvekkil.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name="plans")
public class Plan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@NonNull
	@Column(name="name", nullable=false)
	private String name;
	
	@NonNull
	@Column(name="description", nullable=false)
	private String description;
	
	@NonNull
	@Column(name="monthly_price", nullable=false)
	private BigDecimal monthlyPrice;
	
	@NonNull
	@Column(name="annual_price", nullable=false)
	private BigDecimal annualPrice;
	
	@NonNull
	@Column(name="client_quota", nullable=false)
	private int clientQuota;
	
	@NonNull
	@Column(name="lawyer_quota", nullable=false)
	private int lawyerQuota;
	
	@NonNull
	@Column(name="file_quota_per_client", nullable=false)
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
