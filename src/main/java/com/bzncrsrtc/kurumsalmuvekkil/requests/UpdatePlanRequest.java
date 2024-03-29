package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePlanRequest {

	@NotNull(message="{id.notNull")
	private UUID id;
	
	@NotEmpty(message="{name.notNull}")
	private String name;
	
	@NotEmpty(message="{description.notNull}")
	private String description;
	
	@NotNull(message="{monthlyPrice.notNull}")
	private BigDecimal monthlyPrice;
	
	@NotNull(message="{annualPrice.notNull}")
	private BigDecimal annualPrice;
	
	@NotNull(message="{clientQuota.notNull}")
	private Integer clientQuota;
	
	@NotNull(message="{lawyerQuota.notNull}")
	private Integer lawyerQuota;
	
	@NotNull(message="{fileQuotaPerClient.notNull}")
	private Integer fileQuotaPerClient;
	
}
