package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class FileWithoutCourtResponse {

	private UUID id;
	private String courtDetail;
	private String title;
	private String description;
	
}
