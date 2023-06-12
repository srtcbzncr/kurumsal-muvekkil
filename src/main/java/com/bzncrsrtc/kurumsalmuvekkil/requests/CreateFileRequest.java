package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFileRequest {

	@NotNull(message="{company.id.notNull}")
	private UUID companyId;
	
	@NotEmpty(message="{title.notNull}")
	private String title;
	
	@NotEmpty(message="{description.notNull}")
	private String description;
	
	private UUID courtId;
	
	private String courtDetail;
	
}
