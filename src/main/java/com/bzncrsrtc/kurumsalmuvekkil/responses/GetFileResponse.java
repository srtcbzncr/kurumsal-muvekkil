package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class GetFileResponse {

	private UUID id;
	private GetCourtResponse court;
	private String courtDetail;
	private String title;
	private String description;
	
}
