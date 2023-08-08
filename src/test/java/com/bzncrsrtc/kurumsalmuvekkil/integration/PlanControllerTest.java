package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
	
	
	/* stats endpoint */
	
	@Test
	public void statsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/plan/stats")
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
	
	
	/* create endpoint */
	
	@Test
	public void createWithoutAuthHeaderShouldReturn401() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createWithLawyerRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createWithAdminRoleShouldReturn201() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value(201))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void createEmptyNameWithAdminRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createEmptyDescriptionWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createEmptyMonthlyPriceWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": , \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createEmptyAnnualPriceWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": , \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createEmptyClientQuotaWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": , \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}

	@Test
	public void createEmptyLawyerQuotaWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": , \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createEmptyFileQuotaPerClientWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" :  }";
		
		ResultActions response = mockMvc.perform(post("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* update endpoint */
	
	@Test
	public void updateWithoutAuthHeaderShouldReturn401() throws Exception {
		String request = "{ \"id\": \"" + activePlan.getId() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"id\": \"" + activePlan.getId() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateWithLawyerRoleShouldReturn403() throws Exception {
		String request = "{ \"id\": \"" + activePlan.getId() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateThereIsNoIdWithAdminRoleShouldReturn400() throws Exception {
		String request = "{ \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateNotExistingPlanWithClientRoleShouldReturn404() throws Exception {
		String request = "{ \"id\": \"" + UUID.randomUUID() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateActivePlanWithAdminRoleShouldReturn200() throws Exception {
		String request = "{ \"id\": \"" + activePlan.getId() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void updatePassivePlanWithAdminRoleShouldReturn200() throws Exception {
		String request = "{ \"id\": \"" + passivePlan.getId() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void updateDeletedPlanWithAdminRoleShouldReturn404() throws Exception {
		String request = "{ \"id\": \"" + deletedPlan.getId() + "\", \"name\": \"New Plan\", \"description\": \"Description\", \"monthlyPrice\": 10, \"annualPrice\": 100, \"clientQuota\": 10, \"lawyerQuota\": 10, \"fileQuotaPerClient\" : 10 }";
		
		ResultActions response = mockMvc.perform(put("/plan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
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
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveDeletedPlanWithAdminRole() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + deletedPlan.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveNotExistingPlanShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + UUID.randomUUID() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* setPassive endpoint */
	
	@Test
	public void setPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveDeletedPlanWithAdminRole() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + deletedPlan.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveNotExistingPlanShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + UUID.randomUUID() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/plan/" + passivePlan.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* delete endpoint */
	
	@Test
	public void deleteWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteNotExistingPlanWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteActivePlanWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + activePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void deletePassivePlanWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + passivePlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void deleteDeletedPlanWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/plan/" + deletedPlan.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
}
