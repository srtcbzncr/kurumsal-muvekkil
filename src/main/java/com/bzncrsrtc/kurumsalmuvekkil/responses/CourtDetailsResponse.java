package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CourtDetailsResponse {

	private UUID id;
	private String name;
	private boolean active;
	private boolean deleted;
	private CourtWithoutParentResponse parent;
	private List<CourtWithoutParentResponse> subs;

	
}
