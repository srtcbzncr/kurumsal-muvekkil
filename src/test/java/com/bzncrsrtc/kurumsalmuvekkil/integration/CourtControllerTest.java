package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CourtRepository;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CourtControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private RequestMapper requestMapper;
	
	Court activeRootCourt = new Court("Active Root Court");
	Court passiveRootCourt = new Court("Passive Root Court");
	Court deletedRootCourt = new Court("Deleted Root Court");
	
	Court activeSubCourtOfActiveCourt = new Court("Active Sub Court of Active Root Court");
	Court passiveSubCourtOfActiveCourt = new Court("Passive Sub Court of Active Root Court");
	Court deletedSubCourtOfActiveCourt = new Court("Deleted Sub Court of Active Root Court");
	
	Court activeSubCourtOfPassiveCourt = new Court("Active Sub Court of Passive Root Court");
	Court passiveSubCourtOfPassiveCourt = new Court("Passive Sub Court of Passive Root Court");
	Court deletedSubCourtOfPassiveCourt = new Court("Deleted Sub Court of Passive Root Court");
	
	Court activeSubCourtOfDeletedCourt = new Court("Active Sub Court of Deleted Root Court");
	Court passiveSubCourtOfDeletedCourt = new Court("Passive Sub Court of Deleted Root Court");
	Court deletedSubCourtOfDeletedCourt = new Court("Deleted Sub Court of Deleted Root Court");
	
	@BeforeEach
	public void setup() {
		courtRepository.deleteAll();
		
		courtRepository.save(activeRootCourt);
		
		passiveRootCourt.setActive(false);
		courtRepository.save(passiveRootCourt);
		
		deletedRootCourt.setDeleted(true);
		deletedRootCourt.setDeletedAt(LocalDateTime.now());
		courtRepository.save(deletedRootCourt);
		
		activeSubCourtOfActiveCourt.setParent(activeRootCourt);
		courtRepository.save(activeSubCourtOfActiveCourt);
		
		passiveSubCourtOfActiveCourt.setParent(activeRootCourt);
		passiveSubCourtOfActiveCourt.setActive(false);
		courtRepository.save(passiveSubCourtOfActiveCourt);
		
		deletedSubCourtOfActiveCourt.setParent(activeRootCourt);
		deletedSubCourtOfActiveCourt.setDeleted(true);
		deletedSubCourtOfActiveCourt.setDeletedAt(LocalDateTime.now());
		courtRepository.save(deletedSubCourtOfActiveCourt);
		
		activeSubCourtOfPassiveCourt.setParent(passiveRootCourt);
		courtRepository.save(activeSubCourtOfPassiveCourt);
		
		passiveSubCourtOfPassiveCourt.setParent(passiveRootCourt);
		passiveSubCourtOfPassiveCourt.setActive(false);
		courtRepository.save(passiveSubCourtOfPassiveCourt);
		
		deletedSubCourtOfPassiveCourt.setParent(passiveRootCourt);
		deletedSubCourtOfPassiveCourt.setDeleted(true);
		deletedSubCourtOfPassiveCourt.setDeletedAt(LocalDateTime.now());
		courtRepository.save(deletedSubCourtOfPassiveCourt);
		
		activeSubCourtOfDeletedCourt.setParent(deletedRootCourt);
		courtRepository.save(activeSubCourtOfDeletedCourt);
		
		passiveSubCourtOfDeletedCourt.setParent(deletedRootCourt);
		passiveSubCourtOfDeletedCourt.setActive(false);
		courtRepository.save(passiveSubCourtOfDeletedCourt);
		
		deletedSubCourtOfDeletedCourt.setParent(deletedRootCourt);
		deletedSubCourtOfDeletedCourt.setDeleted(true);
		deletedSubCourtOfDeletedCourt.setDeletedAt(LocalDateTime.now());
		courtRepository.save(deletedSubCourtOfDeletedCourt);
	}
	
	
	private String getAuthorizationHeader(String username) {
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
										.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* statistics endpoint */
	
	@Test
	public void statisticsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/stats")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON)
										.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.allCount").value(8))
				.andExpect(jsonPath("$.data.activeCount").value(4))
				.andExpect(jsonPath("$.data.passiveCount").value(4))
				.andExpect(jsonPath("$.data.deletedCount").value(4))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void statisticsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/stats")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON)
										.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statisticsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/stats")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON)
										.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* subStatistics endpoint */
	
	@Test
	public void subStatisticsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void subStatisticsWithAdminRoleNotExistCourtShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void subStatisticsWithAdminRoleActiveCourtShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.allCount").value(2))
				.andExpect(jsonPath("$.data.activeCount").value(1))
				.andExpect(jsonPath("$.data.passiveCount").value(1))
				.andExpect(jsonPath("$.data.deletedCount").value(1))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void subStatisticsWithAdminRolePassiveCourtShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.allCount").value(2))
				.andExpect(jsonPath("$.data.activeCount").value(1))
				.andExpect(jsonPath("$.data.passiveCount").value(1))
				.andExpect(jsonPath("$.data.deletedCount").value(1))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void subStatisticsWithAdminRoleDeletedCourtShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.allCount").value(2))
				.andExpect(jsonPath("$.data.activeCount").value(1))
				.andExpect(jsonPath("$.data.passiveCount").value(1))
				.andExpect(jsonPath("$.data.deletedCount").value(1))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void subStatisticsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void subStatisticsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findAll endpoint */
	
	@Test
	public void findAllWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(8)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllWithNoAdminRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findAllActive endpoint */
	
	@Test
	public void findAllActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(4)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(4)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(4)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllPassive endpoint */
	
	@Test
	public void findAllPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(4)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllPassiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllDeleted endpoint */
	
	@Test
	public void findAllDeletedWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(4)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllDeletedWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
}
