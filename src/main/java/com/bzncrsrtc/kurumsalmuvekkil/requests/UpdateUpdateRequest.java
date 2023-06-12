package com.bzncrsrtc.kurumsalmuvekkil.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUpdateRequest {

	@NotNull(message="{id.not.null}")
	private UUID id;
	
	@NotNull(message="{file.id.not.null}")
	private UUID fileId;
	
	@NotNull(message="{lawyer.id.not.null}")
	private UUID lawyerId;
	
	@NotNull(message="{state.not.null}")
	private String state;
	
	@NotNull(message="{content.not.null}")
	private String content;
	
}
