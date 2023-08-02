package com.bzncrsrtc.kurumsalmuvekkil.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.bzncrsrtc.kurumsalmuvekkil.repositories.LawyerRepository;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LawyerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
}
