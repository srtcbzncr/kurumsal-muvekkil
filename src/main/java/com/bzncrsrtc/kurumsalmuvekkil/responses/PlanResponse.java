package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class PlanResponse {

	private UUID id;
	private String name;
	private String description;
	private BigDecimal monthlyPrice;
	private BigDecimal annualPrice;
	private int clientQuota;
	private int lawyerQuota;
	private int fileQuotaPerClient;
	
}
