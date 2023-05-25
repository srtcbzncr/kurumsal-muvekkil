package com.bzncrsrtc.kurumsalmuvekkil.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

	

}
