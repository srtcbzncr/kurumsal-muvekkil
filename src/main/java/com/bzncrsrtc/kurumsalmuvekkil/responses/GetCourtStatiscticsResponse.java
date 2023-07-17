package com.bzncrsrtc.kurumsalmuvekkil.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourtStatiscticsResponse {
	
	private int allCount;
	private int activeCount;
	private int passiveCount;
	private int deletedCount;
	
}
