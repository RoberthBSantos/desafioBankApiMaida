package br.com.maida.Bankapi;

import br.com.maida.Bankapi.controller.AccountController;
import br.com.maida.Bankapi.controller.dto.AccountDTO;
import br.com.maida.Bankapi.controller.dto.UserDTO;
import br.com.maida.Bankapi.models.Account;
import br.com.maida.Bankapi.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest
class BankApiApplicationTests {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountController accountService;

	private Map<?,?> getAuthUser() throws JsonProcessingException, Exception {
		User user = new User("jose","jota@maida.com",  "123456");
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON));
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
						.content(objectMapper.writeValueAsString(new ObjectMapper().readValue("{\"email\": \"jota@maida.com\", \"password\": \"123456\"}", Map.class)))
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		return new ObjectMapper().readValue(result.getResponse().getContentAsString(), Map.class);
	}

	@Test
	public void CreateUser() throws Exception {
		User user = new User("joao","joao@maida.com",  "123456");
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
						.content(objectMapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.email").value("joao@maida.com"));
	}

	@Test
	public void authUser() throws Exception {
		User user = new User("joao","joao@maida.com",  "123456");
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON));
		mockMvc.perform(MockMvcRequestBuilders.post("/auth")
						.content(objectMapper.writeValueAsString(new ObjectMapper().readValue("{\"email\": \"joao@maida.com\", \"password\": \"123456\"}", Map.class)))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("joao@maida.com"));
	}


}
