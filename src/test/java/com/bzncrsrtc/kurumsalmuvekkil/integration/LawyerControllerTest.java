package com.bzncrsrtc.kurumsalmuvekkil.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.bzncrsrtc.kurumsalmuvekkil.repositories.LawyerRepository;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({ "classpath:init-data.sql" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LawyerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@BeforeEach
	void setup() {
		
	}
	
}
