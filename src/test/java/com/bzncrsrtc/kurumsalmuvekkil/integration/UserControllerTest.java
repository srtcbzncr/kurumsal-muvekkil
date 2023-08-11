package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.bzncrsrtc.kurumsalmuvekkil.models.Role;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.RoleRepository;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({ "classpath:init-data.sql" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	User activeAdmin = new User("activeAdmin@gmail.com", "activeAdmin", "123456");
	User passiveAdmin = new User("passiveAdmin@gmail.com", "passiveAdmin", "123456");
	User deletedAdmin = new User("deletedAdmin@gmail.com", "deletedAdmin", "123456");
	
	User activeClient = new User("activeClient@gmail.com", "activeClient", "123456");
	User passiveClient = new User("passiveClient@gmail.com", "passiveClient", "123456");
	User deletedClient = new User("deletedClient@gmail.com", "deletedClient", "123456");
	
	User activeLawyer = new User("activeLawyer@gmail.com", "activeLawyer", "123456");
	User passiveLawyer = new User("passiveLawyer@gmail.com", "passiveLawyer", "123456");
	User deletedLawyer = new User("deletedLawyer@gmail.com", "deletedLawyer", "123456");
	
	@BeforeEach
	public void setup() {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
		Role clientRole = roleRepository.findByName("ROLE_CLIENT").get();
		Role lawyerRole = roleRepository.findByName("ROLE_LAWYER").get();
		
		activeAdmin.setRole(adminRole);
		userRepository.save(activeAdmin);
		
		passiveAdmin.setRole(adminRole);
		passiveAdmin.setActive(false);
		userRepository.save(passiveAdmin);
		
		deletedAdmin.setRole(adminRole);
		deletedAdmin.setDeleted(true);
		deletedAdmin.setDeletedAt(LocalDateTime.now());
		userRepository.save(deletedAdmin);
		
		activeClient.setRole(clientRole);
		userRepository.save(activeClient);
		
		passiveClient.setRole(clientRole);
		passiveClient.setActive(false);
		userRepository.save(passiveClient);
		
		deletedClient.setRole(clientRole);
		deletedClient.setDeleted(true);
		deletedClient.setDeletedAt(LocalDateTime.now());
		userRepository.save(deletedClient);
		
		activeLawyer.setRole(lawyerRole);
		userRepository.save(activeLawyer);
		
		passiveLawyer.setRole(lawyerRole);
		passiveAdmin.setActive(false);
		userRepository.save(passiveLawyer);
		
		deletedLawyer.setRole(lawyerRole);
		deletedLawyer.setDeleted(true);
		deletedLawyer.setDeletedAt(LocalDateTime.now());
		userRepository.save(deletedLawyer);
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
		ResultActions response = mockMvc.perform(get("/user/admins/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data.allCount").value(3))
				.andExpect(jsonPath("$.data.activeCount").value(2))
				.andExpect(jsonPath("$.data.passiveCount").value(1))
				.andExpect(jsonPath("$.data.deletedCount").value(1))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void statsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/stats")
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
		ResultActions response = mockMvc.perform(get("/user/admins/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findAllAdmins endpoint */
	
	@Test
	public void findAllAdminsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllAdminsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(3)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void findAllAdminsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllAdminsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}		
	
	
	/* findActiveAdmins endpoint */
	
	@Test
	public void findActiveAdminsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	@Test
	public void findActiveAdminsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/active")
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
	public void findActiveAdminsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	@Test
	public void findActiveAdminsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	
	/* findPassiveAdmins endpoint */
	
	@Test
	public void findPassiveAdminsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	@Test
	public void findPassiveAdminsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/passive")
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
	public void findPassiveAdminsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	@Test
	public void findPassiveAdminsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	
	/* findDeletedAdmins endpoint */
	
	@Test
	public void findDeletedAdminsWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	@Test
	public void findDeletedAdminsWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/deleted")
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
	public void findDeletedAdminsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}	
	
	@Test
	public void findDeletedAdminsWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/user/admins/deleted")
				.contentType(MediaType.APPLICATION_JSON)
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
		ResultActions response = mockMvc.perform(put("/user/" + passiveAdmin.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + passiveAdmin.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void setActiveDeletedUserWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + deletedAdmin.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveNotExistingUserWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + UUID.randomUUID() + "/setActive")
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
		ResultActions response = mockMvc.perform(put("/user/" + passiveAdmin.getId() + "/setActive")
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
		ResultActions response = mockMvc.perform(put("/user/" + passiveAdmin.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	/* setPassive endpoint */
	
	@Test
	public void setPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + activeAdmin.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + activeAdmin.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void setPassiveDeletedUserWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + deletedAdmin.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveNotExistingUserWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(put("/user/" + UUID.randomUUID() + "/setPassive")
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
		ResultActions response = mockMvc.perform(put("/user/" + activeAdmin.getId() + "/setPassive")
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
		ResultActions response = mockMvc.perform(put("/user/" + activeAdmin.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	/* delete endpoint */
	
	@Test
	public void deleteWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(delete("/user/" + activeAdmin.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(delete("/user/" + activeAdmin.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	@Test
	public void deleteDeletedUserWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/user/" + deletedAdmin.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteNotExistingUserWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/user/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));

		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/user/" + activeAdmin.getId())
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
		ResultActions response = mockMvc.perform(delete("/user/" + activeAdmin.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));

		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
}
