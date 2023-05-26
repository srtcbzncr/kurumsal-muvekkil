package com.bzncrsrtc.kurumsalmuvekkil.configurations;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;

@TestConfiguration
public class TestConfig {

	@Bean
	ResponseMapper getResponseMapper() {
		return Mappers.getMapper(ResponseMapper.class);
	}
	
	@Bean
	RequestMapper getRequestMapper() {
		return Mappers.getMapper(RequestMapper.class);
	}
	
}
