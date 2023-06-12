package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bzncrsrtc.kurumsalmuvekkil.models.enums.SubscriptionType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class UpdateSubscriptionRequest {

	@NotNull(message="{id.notNull}")
	private UUID id;
	
	@NotNull(message="{company.id.notNull}")
	private UUID companyId;
	
	@NotNull(message="{plan.id.notNull}")
	private UUID planId;
	
	@NotNull(message="{subscriptionType.notNull}")
	@Valid
	private SubscriptionType type;
	
	@NotNull(message="{fee.notNull}")
	private BigDecimal fee;
	
	@PastOrPresent(message="{invalid.startDate}")
	private LocalDate startDate;
	
	@NotNull(message="{endDate.notNull}")
	@Future(message="{invalid.endDate}")
	private LocalDate endDate;
	
	private boolean autoRenew;
	
}
