package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.PlanRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({ "classpath:init-data.sql" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlanControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PlanRepository planRepository;
	
	Plan activePlan = new Plan("Active Plan", "Active plan", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
	Plan passivePlan = new Plan("Passive Plan", "Passive plan", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
	Plan deletedPlan = new Plan("Deleted Plan", "Deleted plan", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
	
	@BeforeEach
	public void setup() {
		planRepository.deleteAll();
		
		planRepository.save(activePlan);
		
		passivePlan.setActive(false);
		planRepository.save(passivePlan);
		
		deletedPlan.setDeleted(true);
		deletedPlan.setDeletedAt(LocalDateTime.now());
		planRepository.save(deletedPlan);
	}
	
	private String getAuthorizationHeader(String username) {
	    String password = "123456";
	    String auth = username + ":" + password;
	    byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
	    return "Basic " + new String(encodedAuth);
	}
	
	
	/* findAll endpoint */
	
	@Test
	public void findAllWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllActive endpoint */
	
	@Test
	public void findAllActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllPassive endpoint */
	
	@Test
	public void findAllPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllDeleted endpoint */
	
	@Test
	public void findAllDeletedWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findById endpoint */
	
	@Test
	public void findByIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActivePlanWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassivePlanWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + passivePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdDeletedPlanWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + deletedPlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActivePlanWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassivePlanWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + passivePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdDeletedPlanWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + deletedPlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActivePlanWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassivePlanWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + passivePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdDeletedPlanWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + deletedPlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdNotExistingPlanWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}

}
