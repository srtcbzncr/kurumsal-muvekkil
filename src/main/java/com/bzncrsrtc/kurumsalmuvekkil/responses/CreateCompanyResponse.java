package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class CreateCompanyResponse {

	private UUID id;
	
	private String name;
	
	private boolean deleted;
	
	private boolean active;
	
	private LocalDateTime created_at;
	
}
