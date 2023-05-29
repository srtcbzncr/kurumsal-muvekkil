package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreatePlanRequest {

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
