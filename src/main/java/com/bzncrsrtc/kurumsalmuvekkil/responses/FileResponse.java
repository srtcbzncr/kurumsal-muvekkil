package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class FileResponse {

	private UUID id;
	private CourtResponse court;
	private String courtDetail;
	private String title;
	private String description;
	
}
