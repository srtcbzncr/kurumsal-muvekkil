package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public class UpdatePlanRequest {

	@NotEmpty(message="{id.notNull")
	private UUID id;
	
	@NotEmpty(message="{name.notNull}")
	private String name;
	
	@NotEmpty(message="{description.notNull}")
	private String description;
	
	@NotEmpty(message="{monthlyPrice.notNull}")
	private BigDecimal monthlyPrice;
	
	@NotEmpty(message="{annual.notNull}")
	private BigDecimal annualPrice;
	
	@NotEmpty(message="{clientQuota.notNull}")
	private int clienQuota;
	
	@NotEmpty(message="{lawyerQuota.notNull}")
	private int lawyerQuota;
	
	@NotEmpty(message="{fileQuotaPerClient.notNull}")
	private int fileQuotaPerClient;
	
}
