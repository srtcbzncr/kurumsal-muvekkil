package com.bzncrsrtc.kurumsalmuvekkil.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCompanyRequest {

	@NotEmpty(message="{name.notNull}")
	private String name;
	
}
