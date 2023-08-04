package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class CourtResponse {

	private UUID id;
	private String name;
	private CourtWithoutParentResponse parent;
	private int subCount;
	private boolean active;
	private boolean deleted;
	
}
