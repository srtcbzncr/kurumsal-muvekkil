package com.bzncrsrtc.kurumsalmuvekkil.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCourtRequest {
	
	@NotEmpty
	private String name;
}
