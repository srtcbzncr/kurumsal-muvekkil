package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bzncrsrtc.kurumsalmuvekkil.models.enums.SubscriptionType;

import lombok.Data;

@Data
public class SubscriptionResponse {

	private UUID id;
	private CompanyResponse company;
	private PlanResponse plan;
	private SubscriptionType type;
	private BigDecimal fee;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean autoRenew;
	private boolean active;
	private boolean deleted;
	
}
