package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCompanyRequest {
	
	@NotNull(message = "ID can not be null")
	private UUID id;
	
	@NotEmpty(message = "Name can not be null")
	private String name;
	
}
