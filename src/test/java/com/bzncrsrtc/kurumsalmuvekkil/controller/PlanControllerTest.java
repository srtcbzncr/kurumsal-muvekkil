package com.bzncrsrtc.kurumsalmuvekkil.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bzncrsrtc.kurumsalmuvekkil.configurations.TestConfig;
import com.bzncrsrtc.kurumsalmuvekkil.controllers.PlanController;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PlanNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.services.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(TestConfig.class)
@WebMvcTest(PlanController.class)
class PlanControllerTest {
	
	@Autowired
	ResponseMapper responseMapper;
	
	@Autowired
	RequestMapper requestMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	PlanService planService;

	@Test
	public void findAllTest_WithEnAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		// Mock the service
		when(planService.findAll(any())).thenReturn(List.of(new Plan(), new Plan()));
		
		// Perform request
		mockMvc.perform(get("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.header("accept-language", "en"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].id").hasJsonPath())
				.andExpect(jsonPath("$.data[0].name").hasJsonPath())
				.andExpect(jsonPath("$.data[0].description").hasJsonPath())
				.andExpect(jsonPath("$.data[0].monthlyPrice").hasJsonPath())
				.andExpect(jsonPath("$.data[0].annualPrice").hasJsonPath())
				.andExpect(jsonPath("$.data[0].clientQuota").hasJsonPath())
				.andExpect(jsonPath("$.data[0].lawyerQuota").hasJsonPath())
				.andExpect(jsonPath("$.data[0].fileQuotaPerClient").hasJsonPath());
	}
	
	@Test
	public void findAllTest_WithTrAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		// Mock the service
		when(planService.findAll(any())).thenReturn(List.of(new Plan(), new Plan()));
		
		// Perform request
		mockMvc.perform(get("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.header("accept-language", "tr"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].id").hasJsonPath())
				.andExpect(jsonPath("$.data[0].name").hasJsonPath())
				.andExpect(jsonPath("$.data[0].description").hasJsonPath())
				.andExpect(jsonPath("$.data[0].monthlyPrice").hasJsonPath())
				.andExpect(jsonPath("$.data[0].annualPrice").hasJsonPath())
				.andExpect(jsonPath("$.data[0].clientQuota").hasJsonPath())
				.andExpect(jsonPath("$.data[0].lawyerQuota").hasJsonPath())
				.andExpect(jsonPath("$.data[0].fileQuotaPerClient").hasJsonPath());
	}
	
	@Test
	public void findAllTest_WithoutAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		// Mock the service
		when(planService.findAll(any())).thenReturn(List.of(new Plan(), new Plan()));
		
		// Perform request
		mockMvc.perform(get("/plan")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].id").hasJsonPath())
				.andExpect(jsonPath("$.data[0].name").hasJsonPath())
				.andExpect(jsonPath("$.data[0].description").hasJsonPath())
				.andExpect(jsonPath("$.data[0].monthlyPrice").hasJsonPath())
				.andExpect(jsonPath("$.data[0].annualPrice").hasJsonPath())
				.andExpect(jsonPath("$.data[0].clientQuota").hasJsonPath())
				.andExpect(jsonPath("$.data[0].lawyerQuota").hasJsonPath())
				.andExpect(jsonPath("$.data[0].fileQuotaPerClient").hasJsonPath());
	}
	
	@Test
	public void findByIdTest_ExistingPlan_ShouldReturn200() throws Exception {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a plan
		Plan plan = new Plan();
		plan.setId(id);
		
		// Mock the service
		when(planService.findById(any(), any())).thenReturn(plan);
		
		// Perform request
		mockMvc.perform(get("/plan/{id}", id)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.data.id").hasJsonPath())
				.andExpect(jsonPath("$.data.name").hasJsonPath())
				.andExpect(jsonPath("$.data.description").hasJsonPath())
				.andExpect(jsonPath("$.data.monthlyPrice").hasJsonPath())
				.andExpect(jsonPath("$.data.annualPrice").hasJsonPath())
				.andExpect(jsonPath("$.data.clientQuota").hasJsonPath())
				.andExpect(jsonPath("$.data.lawyerQuota").hasJsonPath())
				.andExpect(jsonPath("$.data.fileQuotaPerClient").hasJsonPath());
	}
	
	@Test
	public void findByIdTest_NotExistingPlan_ShouldReturn404() throws Exception {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(planService.findById(any(), any())).thenThrow(new PlanNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(get("/plan/{id}", id)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"));
	}
	
	@Test
	public void createTest_WithValidationErrors_ShouldReturn400() throws Exception {
		// Create a plan
		Plan plan = new Plan();
		
		// Create a CreatePlanRequest
		CreatePlanRequest request = requestMapper.fromPlanToCreatePlanRequest(plan);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Perform request
		mockMvc.perform(post("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.status").value(400));
	}
	
	@Test
	public void createTest_WithoutValidationErrors_ShouldReturn201() throws Exception {
		// Create a plan
		Plan plan = new Plan("Plan", "desc", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
		
		// Create a CreatePlanRequest
		CreatePlanRequest request = requestMapper.fromPlanToCreatePlanRequest(plan);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Create a plan with id
		Plan planWithId = new Plan("Plan", "desc", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
		UUID id = UUID.randomUUID();
		planWithId.setId(id);
		
		// Mock the service
		when(planService.create(any(), any())).thenReturn(planWithId);
		
		// Perform request
		mockMvc.perform(post("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data", containsString(id.toString())))
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(201));
	}
	
	@Test
	public void updateTest_WithValidationErrors_ShouldReturn400() throws Exception {
		// Create a plan
		Plan plan = new Plan();
		UUID id = UUID.randomUUID();
		plan.setId(id);
		
		// Create an UpdatePlanRequest
		UpdatePlanRequest request = requestMapper.fromPlanToUpdatePlanRequest(plan);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Perform request
		mockMvc.perform(put("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.status").value(400));
	}
	
	@Test
	public void updateTest_WithoutValidationErrorsButNotExistingPlan_ShouldReturn404() throws Exception {
		// Create a plan
		Plan plan = new Plan("Plan", "desc", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
		UUID id = UUID.randomUUID();
		plan.setId(id);
			
		// Create an UpdatePlanRequest
		UpdatePlanRequest request = requestMapper.fromPlanToUpdatePlanRequest(plan);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Mock the service
		doThrow(new PlanNotFoundException("Test")).when(planService).update(any(), any());
		
		// Perform request
		mockMvc.perform(put("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void updateTest_WithoutValidationErrorsAndExistingPlan_ShouldReturn200() throws Exception {
		// Create a plan
		Plan plan = new Plan("Plan", "desc", new BigDecimal(10), new BigDecimal(100), 10, 10, 10);
		UUID id = UUID.randomUUID();
		plan.setId(id);
		
		// Create an UpdatePlanRequest
		UpdatePlanRequest request = requestMapper.fromPlanToUpdatePlanRequest(plan);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Perform request
		mockMvc.perform(put("/plan")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void deleteTest_ExistingPlan_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Perform request
		mockMvc.perform(delete("/plan/{id}", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void deleteTest_NotExistingCompany_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		doThrow(new PlanNotFoundException("Test")).when(planService).delete(any(), any());
		
		// Perform request
		mockMvc.perform(delete("/plan/{id}", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
}
