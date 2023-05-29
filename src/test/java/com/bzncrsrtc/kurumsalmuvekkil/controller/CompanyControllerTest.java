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

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bzncrsrtc.kurumsalmuvekkil.configurations.TestConfig;
import com.bzncrsrtc.kurumsalmuvekkil.controllers.CompanyController;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CompanyNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.services.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(TestConfig.class)
@WebMvcTest(CompanyController.class)
class CompanyControllerTest {
	
	@Autowired
	ResponseMapper responseMapper;
	
	@Autowired
	RequestMapper requestMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	CompanyService companyService;
	
	@Test
	public void findAllTest_WithEnAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		// Mock the service
		when(companyService.findAll(any())).thenReturn(List.of(new Company("Company 1"), new Company("Company 2")));
		
		// Perform request
		mockMvc.perform(get("/company")
						.accept(MediaType.APPLICATION_JSON)
						.header("accept-language", "en"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].id").hasJsonPath())
				.andExpect(jsonPath("$.data[0].name").exists());
	}
	
	@Test
	public void findAllTest_WithTrAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		// Mock the service
		when(companyService.findAll(any())).thenReturn(List.of(new Company("Company 1"), new Company("Company 2")));
		
		// Perform request
		mockMvc.perform(get("/company")
						.accept(MediaType.APPLICATION_JSON)
						.header("accept-language", "tr"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].id").hasJsonPath())
				.andExpect(jsonPath("$.data[0].name").exists());
	}
	
	@Test
	public void findAllTest_WithoutAcceptHeaderLanguage_ShouldReturn200() throws Exception {
		// Mock the service
		when(companyService.findAll(any())).thenReturn(List.of(new Company("Company 1"), new Company("Company 2")));
		
		// Perform request
		mockMvc.perform(get("/company")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data[0].id").hasJsonPath())
				.andExpect(jsonPath("$.data[0].name").exists());
	}
	
	@Test
	public void findByIdTest_ExistingCompany_ShouldReturn200() throws Exception {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a company
		Company company = new Company("Company 1");
		company.setId(id);
		
		// Mock the service
		when(companyService.findById(any(), any())).thenReturn(company);
		
		// Perform request
		mockMvc.perform(get("/company/{id}", id)
						.accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.data").isNotEmpty())
			   .andExpect(jsonPath("$.status").value(200))
			   .andExpect(jsonPath("$.error").isEmpty())
			   .andExpect(jsonPath("$.data.id").exists())
			   .andExpect(jsonPath("$.data.name").exists());
	}
	
	@Test
	public void findByIdTest_NotExistingCompanyWithAcceptLanguageHeader_ShouldReturn404() throws Exception {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(companyService.findById(any(), any())).thenThrow(new CompanyNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(get("/company/{id}", id)
						.accept(MediaType.APPLICATION_JSON)
						.header("accept-language", "en"))
			   .andExpect(status().isNotFound())
			   .andExpect(jsonPath("$.data").isEmpty())
			   .andExpect(jsonPath("$.status").value(404))
			   .andExpect(jsonPath("$.error").isNotEmpty())
			   .andExpect(jsonPath("$.error.type").exists())
			   .andExpect(jsonPath("$.error.message").value("Test"));
	}
		
	@Test
	public void createTest_ExistingCompanyName_ShouldReturn201() throws Exception {
		// Create a company
		Company company = new Company("Company 1");
		
		// Create a CreateCompanyRequest
		CreateCompanyRequest request = requestMapper.fromCompanyToCreateCompanyRequest(company);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Create a company with id
		Company companyWithId = new Company("Company 1");
		UUID id = UUID.randomUUID();
		companyWithId.setId(id);
		
		// Mock the service
		when(companyService.create(any(), any())).thenReturn(companyWithId);
		
		// Perform request
		mockMvc.perform(post("/company")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data", containsString(id.toString())))
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(201));   
	}
	
	@Test
	public void createTest_NotExistingCompanyName_ShouldReturn201() throws Exception {
		// Create a company
		Company company = new Company("Company 1");
		
		// Create a CreateCompanyRequest
		CreateCompanyRequest request = requestMapper.fromCompanyToCreateCompanyRequest(company);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Mock the service
		when(companyService.create(any(), any())).thenThrow(new CompanyExistsException("Test"));
		
		// Perform request
		mockMvc.perform(post("/company")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(409));   
	}
	
	@Test
	public void updateTest_ExistingCompany_ShouldReturn200() throws Exception {
		// Create a company
		Company company = new Company("Company 1");
		UUID id = UUID.randomUUID();
		company.setId(id);
		
		// Create an UpdateCompanyRequest
		UpdateCompanyRequest request = requestMapper.fromCompanyToUpdateCompanyRequest(company);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Perform request
		mockMvc.perform(put("/company")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void updateTest_NotExistingCompany_ShouldReturn404() throws Exception {
		// Create a company
		Company company = new Company("Company 1");
		UUID id = UUID.randomUUID();
		company.setId(id);
		
		// Create an UpdateCompanyRequest
		UpdateCompanyRequest request = requestMapper.fromCompanyToUpdateCompanyRequest(company);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(request);
		
		// Mock the service
		doThrow(new CompanyNotFoundException("Test")).when(companyService).update(any(), any());
		
		// Perform request
		mockMvc.perform(put("/company")
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
	public void deleteTest_ExistingCompany_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Perform request
		mockMvc.perform(delete("/company/{id}", id)
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
		doThrow(new CompanyNotFoundException("Test")).when(companyService).delete(any(), any());
		
		// Perform request
		mockMvc.perform(delete("/company/{id}", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void getSubscriptionTest_ExistingCompany_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Perform request
				mockMvc.perform(get("/company/{id}/subscription", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isEmpty())
						.andExpect(jsonPath("$.error").isEmpty())
						.andExpect(jsonPath("$.status").value(200));
	}

	@Test
	public void getSubscriptionTest_NotExistingCompany_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(companyService.getSubscription(any(), any())).thenThrow(new CompanyNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(get("/company/{id}/subscription", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void getLawyersTest_ExistingCompany_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Perform request
				mockMvc.perform(get("/company/{id}/lawyers", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isEmpty())
						.andExpect(jsonPath("$.error").isEmpty())
						.andExpect(jsonPath("$.status").value(200));
	}

	@Test
	public void getLawyersTest_NotExistingCompany_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(companyService.getLawyers(any(), any())).thenThrow(new CompanyNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(get("/company/{id}/lawyers", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void getFilesTest_ExistingCompany_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Perform request
				mockMvc.perform(get("/company/{id}/files", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isEmpty())
						.andExpect(jsonPath("$.error").isEmpty())
						.andExpect(jsonPath("$.status").value(200));
	}

	@Test
	public void getFilesTest_NotExistingCompany_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(companyService.getFiles(any(), any())).thenThrow(new CompanyNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(get("/company/{id}/files", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
}
