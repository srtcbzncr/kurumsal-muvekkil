package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UpdateResponse {

	private UUID id;
	private FileResponse file;
	private LawyerResponse lawyer;
	private String state;
	private String content;
	private LocalDateTime created_at;
	
}
