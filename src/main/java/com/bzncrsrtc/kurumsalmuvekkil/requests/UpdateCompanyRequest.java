package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCompanyRequest {
	
	@NotNull(message = "{id.notNull}")
	private UUID id;
	
	@NotEmpty(message = "{name.notNull}")
	private String name;
	
}
