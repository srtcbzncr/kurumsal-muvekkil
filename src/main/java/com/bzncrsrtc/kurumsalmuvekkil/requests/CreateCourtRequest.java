package com.bzncrsrtc.kurumsalmuvekkil.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCourtRequest {
	
	@NotEmpty(message="{name.notNull}")
	private String name;
	
	private ParentCourtRequest parent;
}
