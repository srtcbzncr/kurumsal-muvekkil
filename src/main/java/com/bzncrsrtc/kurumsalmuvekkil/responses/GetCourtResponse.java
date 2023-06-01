package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class GetCourtResponse {

	private UUID id;
	private String name;
	private UUID parentId;
	private String parentName;
	
}
