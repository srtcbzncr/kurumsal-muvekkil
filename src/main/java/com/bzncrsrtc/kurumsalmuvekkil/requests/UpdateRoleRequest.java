package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateRoleRequest {

	@NotEmpty(message="{id.notNull}")
	private UUID id;
	
	@NotEmpty(message="{name.notNull}")
	private String name;
	
}
