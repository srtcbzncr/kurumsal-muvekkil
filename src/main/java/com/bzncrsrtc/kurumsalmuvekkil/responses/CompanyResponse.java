package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CompanyResponse {

	private UUID id;
	private String name;
	private int lawyerCount;
	private String plan;
	private boolean active;
	private boolean deleted;
	
}
