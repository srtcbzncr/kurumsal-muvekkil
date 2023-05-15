package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class AllCompaniesResponse {
	
	private UUID id;
	
	private String name;
	
	private boolean deleted;
	
	private boolean active;

}
