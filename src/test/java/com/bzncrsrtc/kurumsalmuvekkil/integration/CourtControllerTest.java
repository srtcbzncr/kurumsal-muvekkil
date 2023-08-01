package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCourtRequest;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CourtControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CourtRepository courtRepository;
	
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
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
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
	
	
	/* findAllByParentId endpoint */
	
	@Test
	public void findAllByParentIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllByParentIdNotExistRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllByParentIdActiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllByParentIdPassiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllByParentIdDeletedRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllByParentIdWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllByParentIdWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	/* findAllActiveByParentId endpoint */
	@Test
	public void findAllActiveByParentIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdNotExistRootCourtWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdActiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdPassiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdDeletedRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdNotExistRootCourtWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdActiveRootCourtWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdPassiveRootCourtWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdDeletedRootCourtWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdNotExistRootCourtWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdActiveRootCourtWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdPassiveRootCourtWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveByParentIdDeletedRootCourtWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/subs/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	/* findAllPassiveByParentId endpoint */
	
	@Test
	public void findAllPassiveByParentIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveByParentIdNotExistRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveByParentIdActiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllPassiveByParentIdPassiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllPassiveByParentIdDeletedRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllPassiveByParentIdWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveByParentIdWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	/* findAllDeletedByParentId endpoint */
	
	@Test
	public void findAllDeletedByParentIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedByParentIdNotExistRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedByParentIdActiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllDeletedByParentIdPassiveRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllDeletedByParentIdDeletedRootCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllDeletedByParentIdWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedByParentIdWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId() + "/subs/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findById endpoint */
	
	@Test
	public void findByIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdNotExistCourtWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActiveCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value(activeRootCourt.getName()))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassiveCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value(passiveRootCourt.getName()))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdDeletedCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value(deletedRootCourt.getName()))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdNotExistingCourtWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActiveCourtWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value(activeRootCourt.getName()))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassiveCourtWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdDeletedCourtWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdNotExistingCourtWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActiveCourtWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value(activeRootCourt.getName()))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassiveCourtWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + passiveRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdDeletedCourtWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/court/" + deletedRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	/* create endpoint */
	
	@Test
	public void createWithoutAuthHeaderShouldReturn401() throws Exception {
		
		String request = "{ \"name\": \"New Court\" }";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createWithAdminRoleShouldReturn201() throws Exception {
		
		String request = "{ \"name\": \"New Court\" }";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value(201))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void createNullRequestWithAdminRoleShouldReturn400() throws Exception {
		
		String request = "";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createNotExistParentCourtWithAdminRoleShouldReturn404() throws Exception {
		
		String request = "{ \"name\": \"New Court\", \"parent\" : { \"id\" : \"0836e519-feb4-43f5-b1f6-880c18758b3a\" } }";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createExistParentCourtWithAdminRoleShouldReturn404() throws Exception {
		
		String request = "{ \"name\": \"New Court\", \"parent\" : { \"id\" : \"" + activeRootCourt.getId() + "\" } }";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value(201))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void createWithLawyerRoleShouldReturn403() throws Exception {
		
		String request = "{ \"name\": \"New Court\" }";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createWithClientRoleShouldReturn403() throws Exception {
		
		String request = "{ \"name\": \"New Court\" }";
		
		ResultActions response = mockMvc.perform(post("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* update endpoint */
	
	@Test
	public void updateWithoutAuthHeaderShouldReturn401() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"New New Court\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateWithLawyerRoleShouldReturn403() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"New New Court\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	@Test
	public void updateWithClientRoleShouldReturn403() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"New New Court\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	@Test
	public void updateWithAdminRoleShouldReturn200() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"New New Court\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value("New New Court"))
				.andExpect(jsonPath("$.error").isEmpty());
		
	}
	
	@Test
	public void updateNullRequestWithAdminRoleShouldReturn400() throws Exception {
		
		String request = "";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	@Test
	public void updateNullNameWithAdmnRoleShouldReturn400() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	@Test
	public void updateNotExistCourtWithAdminRoleShouldReturn404() throws Exception {
		
		String request = "{ \"id\": \"" + UUID.randomUUID() + "\", \"name\": \"New New Court\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	@Test
	public void updateDuplicateCourtNameWithAdminRoleShouldReturn409() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"Passive Root Court\" }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isConflict())
				.andExpect(jsonPath("$.status").value(409))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateExistParentCourtWithAdminRoleShouldReturn200() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"New New Court\", \"parent\" : { \"id\": \"" + passiveRootCourt.getId() + "\" } }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value("New New Court"))
				.andExpect(jsonPath("$.data.parent.name").value(passiveRootCourt.getName()))
				.andExpect(jsonPath("$.error").isEmpty());
		
	}
	
	@Test
	public void updateNotExistParentCourtWithAdminRoleShouldReturn404() throws Exception {
		
		String request = "{ \"id\": \"" + activeRootCourt.getId() + "\", \"name\": \"New New Court\", \"parent\" : { \"id\": \"" + UUID.randomUUID() + "\" } }";
		
		ResultActions response = mockMvc.perform(put("/court")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	
	/* setActive endpoint */
	
	@Test
	public void setActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + passiveRootCourt.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + passiveRootCourt.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + passiveRootCourt.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveNotExistCourtWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + UUID.randomUUID() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveExistCourtWithAdmnRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + passiveRootCourt.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.active").value(true))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* setActive endpoint */
	
	@Test
	public void setPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + activeRootCourt.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + activeRootCourt.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + activeRootCourt.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveNotExistCourtWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + UUID.randomUUID() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveExistCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/court/" + activeRootCourt.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.active").value(false))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* delete endpoint */
	
	@Test
	public void deleteWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(delete("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteNotExistCourtWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/court/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteExistCourtWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(delete("/court/" + activeRootCourt.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
}
