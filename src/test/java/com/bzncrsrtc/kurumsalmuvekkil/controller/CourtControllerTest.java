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
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bzncrsrtc.kurumsalmuvekkil.configurations.TestConfig;
import com.bzncrsrtc.kurumsalmuvekkil.controllers.CourtController;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtExistsException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.CourtNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.PlanNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.services.CourtService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(TestConfig.class)
@WebMvcTest(CourtController.class)
class CourtControllerTest {
	
	@Autowired
	ResponseMapper responseMapper;
	
	@Autowired
	RequestMapper requestMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	CourtService courtService;

	@Test
	public void findAllTest() throws Exception {
		// Mock the service
		when(courtService.findAll(any())).thenReturn(List.of(new Court(), new Court()));
		
		// Perform request
		mockMvc.perform(get("/court")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isArray())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.error").isEmpty())
		.andExpect(jsonPath("$.status").value(200))
		.andExpect(jsonPath("$.data[0].id").hasJsonPath())
		.andExpect(jsonPath("$.data[0].name").hasJsonPath())
		.andExpect(jsonPath("$.data[0].parent").hasJsonPath());
				
	}
	
	@Test
	public void findByIdTest_ExistingCourt_ShouldReturn200() throws Exception {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create a plan
		Court court = new Court();
		court.setId(id);
		
		// Mock the service
		when(courtService.findById(any(), any())).thenReturn(court);
		
		// Perform request
		mockMvc.perform(get("/court/{id}", id)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.data.id").hasJsonPath())
				.andExpect(jsonPath("$.data.name").hasJsonPath())
				.andExpect(jsonPath("$.data.parent").hasJsonPath());
	}
	
	@Test
	public void findByIdTest_NotExistingCourt_ShouldReturn404() throws Exception {
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(courtService.findById(any(), any())).thenThrow(new CourtNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(get("/court/{id}", id)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"));
	}
	
	@Test
	public void createTest_NotExistingCourt_ShouldReturn201() throws Exception {
		// Create a court
		Court court = new Court("Court");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Create random id7
		UUID id = UUID.randomUUID();
		
		// Create a court with id
		Court courtWithId = new Court("Court");
		courtWithId.setId(id);
		
		// Mock the service
		when(courtService.create(any(), any())).thenReturn(courtWithId);
		
		// Perform request
		mockMvc.perform(post("/court")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data", containsString(id.toString())))
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(201));
	}
	
	@Test
	public void createTest_ExistingCourt_ShouldReturn409() throws Exception {
		// Create a court
		Court court = new Court("Court");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Mock the service
		when(courtService.create(any(), any())).thenThrow(new CourtExistsException("Test"));
		
		// Perform request
		mockMvc.perform(post("/court")
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
	public void createTest_WithValidationErrors_ShouldReturn400() throws Exception {
		// Create a court
		Court court = new Court("");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Perform request
		mockMvc.perform(post("/court")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.status").value(400));
	}
	
	@Test
	public void addTest_ExistingParentCourtNotExistingCourt_ShouldReturn201() throws Exception {
		// Create a court
		Court court = new Court("Court");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Create parent court
		Court parent = new Court("Parent");
		parent.setId(id);
		
		// Create a court for returning the service
		Court savedCourt = new Court("Court");
		savedCourt.setParent(parent);
		
		// Mock the service
		when(courtService.add(any(), any(), any())).thenReturn(savedCourt);
		
		// Perform request
		mockMvc.perform(post("/court/{id}/add", id)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data", containsString(id.toString())))
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(201));
	}
	
	@Test
	public void addTest_NotExistingParentCourt_ShouldReturn404() throws Exception {
		// Create a court
		Court court = new Court("Court");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Create a random id
		UUID id = UUID.randomUUID();

		// Mock the service
		when(courtService.add(any(), any(), any())).thenThrow(new CourtNotFoundException("Test"));
		
		// Perform request
		mockMvc.perform(post("/court/{id}/add", id)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").isNotEmpty())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void addTest_WithValidationErrors_ShouldReturn400() throws Exception {
		// Create a court
		Court court = new Court("");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Perform request
		mockMvc.perform(post("/court/{id}/add", id)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.status").value(400));
	}
	
	@Test
	public void addTest_ExistingCourt_ShouldReturn409() throws Exception {
		// Create a court
		Court court = new Court("Court");
		
		// Create a CreateCourtRequest
		CreateCourtRequest createCourtRequest = requestMapper.fromCourtToCreateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(createCourtRequest);
		
		// Create a random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		when(courtService.add(any(), any(), any())).thenThrow(new CourtExistsException("Test"));
		
		// Perform request
		mockMvc.perform(post("/court/{id}/add", id)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").isNotEmpty())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(409));
	}
	
	@Test
	public void updateTest_ExistingCourtWithoutValidationError_ShouldReturn200() throws Exception {
		// Create a court
		Court court = new Court("Court");
		court.setId(UUID.randomUUID());
		
		// Create a UpdateCourtRequest
		UpdateCourtRequest updateCourtRequest = requestMapper.fromCourtToUpdateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(updateCourtRequest);
		
		// Perform request
		mockMvc.perform(put("/court")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void updateTest_WithValidationErrors_ShouldReturn400() throws Exception {
		// Create a court
		Court court = new Court("Court");
		
		// Create a CreateCourtRequest
		UpdateCourtRequest updateCourtRequest = requestMapper.fromCourtToUpdateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(updateCourtRequest);
		
		// Perform request
		mockMvc.perform(put("/court")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.status").value(400));
	}
	
	@Test
	public void updateTest_NotExistingCourt_ShouldReturn404() throws Exception {
		// Create a court
		Court court = new Court("Court");
		court.setId(UUID.randomUUID());
		
		// Create a CreateCourtRequest
		UpdateCourtRequest updateCourtRequest = requestMapper.fromCourtToUpdateCourtRequest(court);
		
		// Create Json request
		ObjectMapper mapper = new ObjectMapper();
		String jsonRequest = mapper.writeValueAsString(updateCourtRequest);
		
		// Mock the service
		doThrow(new CourtNotFoundException("Test")).when(courtService).update(any(), any());
		
		// Perform request
		mockMvc.perform(put("/court")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").isNotEmpty())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void deleteTest_ExistingCourt_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Perform request
		mockMvc.perform(delete("/court/{id}", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty())
				.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void deleteTest_NotExistingCourt_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		// Mock the service
		doThrow(new CourtNotFoundException("Test")).when(courtService).delete(any(), any());
		
		// Perform request
		mockMvc.perform(delete("/court/{id}", id)
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.error.type").exists())
				.andExpect(jsonPath("$.error.message").value("Test"))
				.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void getParentTest_ExistingCourt_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		when(courtService.getParent(any(), any())).thenReturn(new Court("Parent"));
		
		// Perform request
				mockMvc.perform(get("/court/{id}/parent", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isNotEmpty())
						.andExpect(jsonPath("$.error").isEmpty())
						.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void getParentTest_NotExistingCourt_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		when(courtService.getParent(any(), any())).thenThrow(new CourtNotFoundException("Test"));
		
		// Perform request
				mockMvc.perform(get("/court/{id}/parent", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.data").isEmpty())
						.andExpect(jsonPath("$.error").isNotEmpty())
						.andExpect(jsonPath("$.error.type").isNotEmpty())
						.andExpect(jsonPath("$.error.message").value("Test"))
						.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void getSubsTest_ExistingCourt_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		when(courtService.getSubs(any(), any())).thenReturn(List.of(new Court(), new Court()));
		
		// Perform request
				mockMvc.perform(get("/court/{id}/subs", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isNotEmpty())
						.andExpect(jsonPath("$.error").isEmpty())
						.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void getSubsTest_NotExistingCourt_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		when(courtService.getSubs(any(), any())).thenThrow(new CourtNotFoundException("Test"));
		
		// Perform request
				mockMvc.perform(get("/court/{id}/subs", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.data").isEmpty())
						.andExpect(jsonPath("$.error").isNotEmpty())
						.andExpect(jsonPath("$.error.type").isNotEmpty())
						.andExpect(jsonPath("$.error.message").value("Test"))
						.andExpect(jsonPath("$.status").value(404));
	}
	
	@Test
	public void getFilesTest_ExistingCourt_ShouldReturn200() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		when(courtService.getFiles(any(), any())).thenReturn(List.of(new File(), new File()));
		
		// Perform request
				mockMvc.perform(get("/court/{id}/files", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isNotEmpty())
						.andExpect(jsonPath("$.error").isEmpty())
						.andExpect(jsonPath("$.status").value(200));
	}
	
	@Test
	public void getFilesTest_NotExistingCourt_ShouldReturn404() throws Exception {
		// Create random id
		UUID id = UUID.randomUUID();
		
		when(courtService.getFiles(any(), any())).thenThrow(new CourtNotFoundException("Test"));
		
		// Perform request
				mockMvc.perform(get("/court/{id}/files", id)
						        .accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.data").isEmpty())
						.andExpect(jsonPath("$.error").isNotEmpty())
						.andExpect(jsonPath("$.error.type").isNotEmpty())
						.andExpect(jsonPath("$.error.message").value("Test"))
						.andExpect(jsonPath("$.status").value(404));
	}
}
