package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bzncrsrtc.kurumsalmuvekkil.models.enums.SubscriptionType;

import lombok.Data;

@Data
public class SubscriptionWithoutPlanResponse {

	private UUID id;
	private CompanyResponse company;
	private SubscriptionType type;
	private BigDecimal fee;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean autoRenew;
	
}
