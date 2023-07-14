package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCourtRequest {
	
	@NotEmpty(message="{name.notNull}")
	private String name;
	
	private UUID parentId;
}
