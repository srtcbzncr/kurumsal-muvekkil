package com.bzncrsrtc.kurumsalmuvekkil.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bzncrsrtc.kurumsalmuvekkil.controllers.CompanyController;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.services.CompanyService;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ResponseMapper responseMapper;
	
	@Autowired
	RequestMapper requestMapper;
	
	@MockBean
	CompanyService companyService;
	
	@Test
	public void findAllTest_WithEnAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		mockMvc.perform(get("/company")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
