package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class GetCourtDetailsResponse {

	private UUID id;
	private String name;
	private boolean active;
	private boolean deleted;
	private GetCourtWithoutParentResponse parent;
	private List<GetCourtWithoutParentResponse> subs;

	
}
