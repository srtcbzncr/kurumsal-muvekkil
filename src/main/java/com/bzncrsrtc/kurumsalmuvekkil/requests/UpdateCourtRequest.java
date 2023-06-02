package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCourtRequest {

	@NotNull
	private UUID id;
	
	@NotEmpty
	private String name;
	
}
