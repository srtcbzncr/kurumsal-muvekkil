package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class GetFileWithoutCourtResponse {

	private UUID id;
	private String courtDetail;
	private String title;
	private String description;
	
}
