package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CourtRepository;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CourtIntegrationTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private RequestMapper requestMapper;
	
	@BeforeEach
	void setup() {
		courtRepository.deleteAll();
		
		Court activeRootCourt = new Court("Active Root Court");
		courtRepository.save(activeRootCourt);
		
		Court passiveRootCourt = new Court("Passive Root Court");
		passiveRootCourt.setActive(false);
		courtRepository.save(passiveRootCourt);
		
		Court deletedRootCourt = new Court("Deleted Root Court");
		deletedRootCourt.setDeleted(true);
		deletedRootCourt.setDeletedAt(LocalDateTime.now());
		courtRepository.save(deletedRootCourt);
		
		Court activeCourt = new Court("Active Court");
		activeCourt.setParent(activeRootCourt);
		courtRepository.save(activeCourt);
		
		Court passiveCourt = new Court("Passive Court");
		passiveCourt.setActive(false);
		passiveCourt.setParent(activeRootCourt);
		courtRepository.save(passiveCourt);
		
		Court deletedCourt = new Court("Deleted Court");
		deletedCourt.setParent(activeRootCourt);
		deletedCourt.setDeleted(true);
		deletedCourt.setDeletedAt(LocalDateTime.now());
		courtRepository.save(deletedCourt);
	}
	
	private String getAuthorizationHeader() {
	    String username = "admin";
	    String password = "123456";
	    String auth = username + ":" + password;
	    byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
	    return "Basic " + new String(encodedAuth);
	}
	
	@Sql(scripts={"classpath:data.sql"})
	
	@Test
	public void statisticsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Accept-Language", "tr"));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statisticsWithAuthHeaderShouldReturnCourtStatistics() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader())
				.header("Accept-Language", "tr"));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data.allCount").value(4))
				.andExpect(jsonPath("$.data.activeCount").value(2))
				.andExpect(jsonPath("$.data.passiveCount").value(2))
				.andExpect(jsonPath("$.data.deletedCount").value(2))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
}
