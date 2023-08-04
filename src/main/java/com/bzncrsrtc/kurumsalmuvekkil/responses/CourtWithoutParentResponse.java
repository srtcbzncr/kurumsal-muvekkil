package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class CourtWithoutParentResponse {
	
	private UUID id;
	private String name;
	private int subCount;
	private boolean active;
	private boolean deleted;
	
}
