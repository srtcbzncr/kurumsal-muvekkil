package com.bzncrsrtc.kurumsalmuvekkil.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.ClientRepository;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql({ "classpath:init-data.sql" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// Users
	User activeClientUser = new User("activeClient@gmail.com", "activeClient", "123456");
	User passiveClientUser = new User("passiveClient@gmail.com", "passiveClient", "123456");
	User deletedClientUser = new User("deletedClient@gmail.com", "deletedClient", "123456");
	
	// Clients
	Client activeClient = new Client(activeClientUser, "11111111111", "active", "client", "5555555555");
	Client passiveClient = new Client(passiveClientUser, "22222222222", "passive", "client", "6666666666");
	Client deletedClient = new Client(deletedClientUser, "33333333333", "deleted", "client", "7777777777");
	
	@BeforeEach
	public void setup() {
		clientRepository.deleteAll();
		
		clientRepository.save(activeClient);
		
		passiveClientUser.setActive(false);
		passiveClient.setActive(false);
		clientRepository.save(passiveClient);
		
		deletedClientUser.setDeleted(true);
		deletedClientUser.setDeletedAt(LocalDateTime.now());
		deletedClient.setDeleted(true);
		deletedClient.setDeletedAt(LocalDateTime.now());
		clientRepository.save(deletedClient);
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
		ResultActions response = mockMvc.perform(get("/client/stats")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void statsWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/stats")
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
		ResultActions response = mockMvc.perform(get("/client/stats")
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
		ResultActions response = mockMvc.perform(get("/client/stats")
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
		ResultActions response = mockMvc.perform(get("/client/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/all")
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
		ResultActions response = mockMvc.perform(get("/client/all")
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
		ResultActions response = mockMvc.perform(get("/client/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		System.out.println(response);
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* findAllActive endpoint */
	
	@Test
	public void findAllActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("client")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveWithLawyerRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/active")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllActiveWithAdminRoleShouldReturn200() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/active")
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
		ResultActions response = mockMvc.perform(get("/client/passive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/passive")
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
		ResultActions response = mockMvc.perform(get("/client/passive")
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
		ResultActions response = mockMvc.perform(get("/client/passive")
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
		ResultActions response = mockMvc.perform(get("/client/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void findAllDeletedWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(get("/client/deleted")
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
		ResultActions response = mockMvc.perform(get("/client/deleted")
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
		ResultActions response = mockMvc.perform(get("/client/deleted")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.error").isEmpty());
	}
	
	
	/* setActive endpoint */
	
	@Test
	public void setActiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + passiveClient.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + passiveClient.getId() + "/setActive")
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
		ResultActions response = mockMvc.perform(put("/client/" + passiveClient.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveNotExistingClientWithAdminRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + UUID.randomUUID() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setActiveDeletedClientWithAdminRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + deletedClient.getId() + "/setActive")
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
		ResultActions response = mockMvc.perform(put("/client/" + passiveClient.getId() + "/setActive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
		
		assertTrue(userRepository.findById(passiveClient.getUser().getId()).get().isActive());
	}
	
	
	/* setPassive endpoint */
	
	@Test
	public void setPassiveWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + activeClient.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + activeClient.getId() + "/setPassive")
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
		ResultActions response = mockMvc.perform(put("/client/" + activeClient.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveNotExistingClientWithAdminRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + UUID.randomUUID() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void setPassiveDeletedClientWithAdminRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(put("/client/" + deletedClient.getId() + "/setPassive")
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
		ResultActions response = mockMvc.perform(put("/client/" + activeClient.getId() + "/setPassive")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
		
		assertFalse(userRepository.findById(passiveClient.getUser().getId()).get().isActive());
	}
	
	
	/* delete endpoint */
	
	@Test
	public void deleteWithoutAuthHeaderShouldReturn401() throws Exception {
		ResultActions response = mockMvc.perform(delete("/client/" + activeClient.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
		
	}
	
	@Test
	public void deleteWithClientRoleShouldReturn403() throws Exception {
		ResultActions response = mockMvc.perform(delete("/client/" + activeClient.getId())
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
		ResultActions response = mockMvc.perform(delete("/client/" + activeClient.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("lawyer")));
		
		response.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteNotExistingClientWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/client/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isNotEmpty());
	}
	
	@Test
	public void deleteDeletedClientWithAdminRoleShouldReturn404() throws Exception {
		ResultActions response = mockMvc.perform(delete("/client/" + deletedClient.getId())
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
		ResultActions response = mockMvc.perform(delete("/client/" + activeClient.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuthorizationHeader("admin")));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.error").isEmpty());
		
		assertTrue(userRepository.findById(activeClient.getUser().getId()).get().isDeleted());
	}

}
