package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePlanRequest {

	@NotEmpty(message="{name.notNull}")
	private String name;
	
	@NotEmpty(message="{description.notNull}")
	private String description;
	
	@NotNull(message="{monthlyPrice.notNull}")
	@Min(value=0, message="{monthlyPrice.minError}")
	private BigDecimal monthlyPrice;
	
	@NotNull(message="{annualPrice.notNull}")
	@Min(value=0, message="{annualPrice.minError}")
	private BigDecimal annualPrice;
	
	@NotNull(message="{clientQuota.notNull}")
	@Min(value=0, message="{clientQuota.minError}")
	private Integer clientQuota;
	
	@NotNull(message="{lawyerQuota.notNull}")
	@Min(value=0, message="{lawyerQuota.minError}")
	private Integer lawyerQuota;
	
	@NotNull(message="{fileQuotaPerClient.notNull}")
	@Min(value=0, message="{fileQuotaPerClient.minError}")
	private Integer fileQuotaPerClient;
	
}
