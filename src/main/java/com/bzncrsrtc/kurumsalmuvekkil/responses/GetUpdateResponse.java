package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class GetUpdateResponse {

	private UUID id;
	private GetFileResponse file;
	private GetLawyerResponse lawyer;
	private String state;
	private String content;
	private LocalDateTime created_at;
	
}
