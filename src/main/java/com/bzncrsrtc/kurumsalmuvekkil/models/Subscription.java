package com.bzncrsrtc.kurumsalmuvekkil.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bzncrsrtc.kurumsalmuvekkil.models.enums.SubscriptionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name="subscriptions")
public class Subscription {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@NonNull
	@ManyToOne()
	@JoinColumn(name="company_id", referencedColumnName="id")
	private Company company;
	
	@NonNull
	@ManyToOne()
	@JoinColumn(name="plan_id", referencedColumnName="id")
	private Plan plan;
	
	@NonNull
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private SubscriptionType type;
	
	@NonNull
	@Column(name="fee")
	private BigDecimal fee;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="auto_renew")
	private boolean autoRenew = true;
	
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
	
	public Subscription(Company company, Plan plan, SubscriptionType type) {
		this.company = company;
		this.plan = plan;
		this.type = type;
		this.fee = type == SubscriptionType.MONTHLY ? plan.getMonthlyPrice() : plan.getAnnualPrice();
		this.startDate = LocalDate.now();
		this.endDate = type == SubscriptionType.MONTHLY ? this.startDate.plusMonths(1) : this.startDate.plusYears(1);
		
	}

}
