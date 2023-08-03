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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({ "classpath:init-data.sql" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompanyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CompanyRepository companyRepository;

	// Companies
	Company activeCompany1 = new Company("Active Company 1");
	Company activeCompany2 = new Company("Active Company 2");
	Company passiveCompany = new Company("Passive Company");
	Company deletedCompany = new Company("Deleted Company");

	@BeforeEach
	public void setup() {
		// Add companies
		companyRepository.deleteAll();
		
		companyRepository.save(activeCompany1);
		
		companyRepository.save(activeCompany2);
		
		passiveCompany.setActive(false);
		companyRepository.save(passiveCompany);
		
		deletedCompany.setDeleted(true);
		deletedCompany.setDeletedAt(LocalDateTime.now());
		companyRepository.save(deletedCompany);
	}
	
	private String getAuthorizationHeader(String username) {		
	    String password = "123456";
	    String auth = username + ":" + password;
	    byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
	    return "Basic " + new String(encodedAuth);
	}
	
	
	/* findAll endpoint */

	public void findAllWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/all")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithAdminRoleShouldReturn200() throws Exception {		
		ResultActions response = mockMvc.perform(get("/company/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data", hasSize(3)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findAllActive endpoint */
	
	@Test
	public void findAllActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/active")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/active")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/active")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllActiveWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/active")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllPassive endpoint */
	
	@Test
	public void findAllPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/passive")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/passive")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/passive")
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
		ResultActions response = mockMvc.perform(get("/company/passive")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findAllDeleted endpoint */
	
	@Test
	public void findAllDeletedWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/deleted")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/deleted")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllDeletedWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/deleted")
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
		ResultActions response = mockMvc.perform(get("/company/deleted")
				                        .contentType(MediaType.APPLICATION_JSON)
				                        .accept(MediaType.APPLICATION_JSON)
				                        .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findById endpoint */
	
	@Test
	public void findByIdWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdNotExistCompanyWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdNotExistCompanyWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdNotExistCompanyWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActiveCompanyWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassiveCompanyWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + passiveCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdDeletedCompanyWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + deletedCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdActiveCompanyWithClientRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassiveCompanyWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + passiveCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdDeletedCompanyWithClientRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + deletedCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdActiveCompanyWithLawyerRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findByIdPassiveCompanyWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + passiveCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findByIdDeletedCompanyWithLawyerRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/" + deletedCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* create endpoint */
	
	@Test
	public void createWithoutAuthHeaderShouldReturn401() throws Exception {
		String request = "{ \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test 
	public void createWithAdminRoleShouldReturn201() throws Exception {
		String request = "{ \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(post("/company")
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
	public void createEmptyNameWithAdminRoleShouldReturn400() throws Exception {
		String request = "{ \"name\": \"\" }";
		
		ResultActions response = mockMvc.perform(post("/company")
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
	public void createAlreadyExistCompanyWithAdminRoleShouldReturn409() throws Exception {
		String request = "{ \"name\": \"Active Company 1\" }";
		
		ResultActions response = mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isConflict())
				.andExpect(jsonPath("$.status").value(409))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void createEmptyRequestWithAdminRoleShouldReturn400() throws Exception {
		String request = "";
		
		ResultActions response = mockMvc.perform(post("/company")
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
	public void createWithClientRoleShouldReturn403() throws Exception {
		String request = "{ \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(post("/company")
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
		String request = "{ \"name\": \"New Court\" }";
		
		ResultActions response = mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* update endpoint */
	
	@Test
	public void updateWithoutAuthHeaderShouldReturn401() throws Exception {
		String request = "{ \"id\": \"" + activeCompany1.getId() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateNotExistingCompanyWithAdminRoleShouldReturn404() throws Exception {
		String request = "{ \"id\": \"" + UUID.randomUUID() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateExistingNameWithAdminRoleShouldReturn409() throws Exception {
		String request = "{ \"id\": \"" + activeCompany1.getId() + "\", \"name\": \"Active Company 2\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isConflict())
				.andExpect(jsonPath("$.status").value(409))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void updateEmptyNameWithAdminRoleShouldReturn400() throws Exception {
		String request = "{ \"id\": \"" + activeCompany1.getId() + "\", \"name\": \"\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateEmptyIdWithAdminRoleShouldReturn400() throws Exception {
		String request = "{ \"id\": \"" + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateEmptyRequestWithAdminRoleShouldReturn400() throws Exception {
		String request = "";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateDeletedCompanyWithAdminRoleShouldReturn404() throws Exception {
		String request = "{ \"id\": \"" + deletedCompany.getId() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updatePassiveCompanyWithAdminRoleShouldReturn200() throws Exception {
		String request = "{ \"id\": \"" + passiveCompany.getId() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateActiveCompanyWithAdminRoleShouldReturn200() throws Exception {
		String request = "{ \"id\": \"" + activeCompany1.getId() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateWithClientRoleShouldReturn401() throws Exception {
		String request = "{ \"id\": \"" + activeCompany1.getId() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
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
	public void updateWithLawyerRoleShouldReturn401() throws Exception {
		String request = "{ \"id\": \"" + activeCompany1.getId() + "\", \"name\": \"New Company\" }";
		
		ResultActions response = mockMvc.perform(put("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* setActive endpoint */
	
	@Test
	public void setActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + passiveCompany.getId() + "/setActive")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveNotExistingCompanyWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + UUID.randomUUID() + "/setActive")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveDeletedCompanyWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + deletedCompany.getId() + "/setActive")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + passiveCompany.getId() + "/setActive" )
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
		ResultActions response = mockMvc.perform(put("/company/" + passiveCompany.getId() + "/setActive" )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + passiveCompany.getId() + "/setActive" )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	/* setActive endpoint */
	
	@Test
	public void setPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + activeCompany1.getId() + "/setPassive")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveNotExistingCompanyWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + UUID.randomUUID() + "/setPassive")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveDeletedCompanyWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + deletedCompany.getId() + "/setPassive")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + activeCompany1.getId() + "/setPassive" )
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
		ResultActions response = mockMvc.perform(put("/company/" + activeCompany1.getId() + "/setPassive" )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/company/" + activeCompany1.getId() + "/setPassive" )
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
		ResultActions response = mockMvc.perform(delete("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/company/" + activeCompany1.getId())
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
		ResultActions response = mockMvc.perform(delete("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteNotExistingCompanyWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/company/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(delete("/company/" + activeCompany1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
}
