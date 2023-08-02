package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.CompanyRepository;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.RoleRepository;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	Company activeCompany1 = new Company("Active Company 1");
	Company activeCompany2 = new Company("Active Company 2");
	Company passiveCompany = new Company("Passive Company");
	Company deletedCompany = new Company("Deleted Company");
	
	@BeforeEach
	public void setup() {
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
	
	@Test
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:init-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	})
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
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:init-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	})
	public void findAllWithAdminRoleShouldReturn200() throws Exception {
		
		System.out.println("Users");
		List<User> users = userRepository.findAll();
		System.out.println(users.size());
		
		ResultActions response = mockMvc.perform(get("/court/all")
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
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:init-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	})
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
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:init-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	})
	public void findAllWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/company/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
}
