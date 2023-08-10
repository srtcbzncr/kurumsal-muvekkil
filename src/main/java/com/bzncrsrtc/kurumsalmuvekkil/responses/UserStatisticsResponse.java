package com.bzncrsrtc.kurumsalmuvekkil.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatisticsResponse {
	
	private int allCount;
	private int activeCount;
	private int passiveCount;
	private int deletedCount;
	
}
