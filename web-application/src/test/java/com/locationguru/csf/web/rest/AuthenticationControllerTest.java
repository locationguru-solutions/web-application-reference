package com.locationguru.csf.web.rest;

import java.util.UUID;

import com.locationguru.csf.model.Authentication;
import com.locationguru.csf.model.User;
import com.locationguru.csf.model.support.AuthenticationType;
import com.locationguru.csf.repository.AuthenticationRepository;
import com.locationguru.csf.repository.UserRepository;
import com.locationguru.csf.security.util.EncryptionUtils;
import com.locationguru.support.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTest
		extends BaseTest
{
	private static final Logger logger = LogManager.getLogger(AuthenticationControllerTest.class);

	private final MockMvc mvc;

	@MockBean
	private AuthenticationRepository repository;

	@MockBean
	private UserRepository userRepository;

	private String authorization;
	private User user;

	@Autowired
	public AuthenticationControllerTest(final MockMvc mvc)
	{
		this.mvc = mvc;
	}

	@Test
	@Order(1)
	void login() throws Exception
	{
		final String username = UUID.randomUUID().toString().split("-")[4];
		final String password = UUID.randomUUID().toString().split("-")[4];

		final Authentication authentication = createAuthentication(username, password, AuthenticationType.USER_NAME);

		Mockito.when(repository.findByIdentityAndType(username, AuthenticationType.USER_NAME)).thenReturn(authentication);

		final MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/authentications/login")
																   .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
																   .accept(MediaType.APPLICATION_JSON)
																   .param("username", username)
																   .param("password", password))
									.andExpect(MockMvcResultMatchers.status().isOk())
									.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
									.andExpect(header().exists("Authorization"))
									.andReturn();

		final String authorization = result.getResponse().getHeader("Authorization");

		Assertions.assertNotNull(authorization);
		Assertions.assertNotEquals(0, authorization.length());

		logger.info("Authorization : {}", authorization);

		this.authorization = authorization;
		this.user = authentication.getUser();
	}

	@Test
	@Order(2)
	void logout() throws Exception
	{
		Mockito.when(userRepository.findByUid(user.getUid())).thenReturn(user);

		mvc.perform(MockMvcRequestBuilders.post("/authentications/logout")
										  .accept(MediaType.APPLICATION_JSON)
										  .header("Authorization", authorization))
		   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
		   .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void check() throws Exception
	{
		final String apiKey = UUID.randomUUID().toString();
		final Authentication authentication = createAuthentication(apiKey, "", AuthenticationType.API_KEY);

		Mockito.when(repository.findByIdentityAndType(apiKey, AuthenticationType.API_KEY)).thenReturn(authentication);

		// Checking positive scenario
		mvc.perform(MockMvcRequestBuilders.post("/authentications/check")
										  .accept(MediaType.APPLICATION_JSON)
										  .header("Authorization", "ApiKey " + apiKey))
		   .andExpect(MockMvcResultMatchers.status().isOk());

		// Checking negative scenario
		mvc.perform(MockMvcRequestBuilders.post("/authentications/check")
										  .accept(MediaType.APPLICATION_JSON)
										  .header("Authorization", "ApiKey wrong-key"))
		   .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));

	}

	@Test
	void logoutWithApiKey() throws Exception
	{
		final String apiKey = UUID.randomUUID().toString();
		final Authentication authentication = createAuthentication(apiKey, "", AuthenticationType.API_KEY);

		Mockito.when(repository.findByIdentityAndType(apiKey, AuthenticationType.API_KEY)).thenReturn(authentication);

		mvc.perform(MockMvcRequestBuilders.post("/authentications/logout")
										  .accept(MediaType.APPLICATION_JSON)
										  .header("Authorization", "ApiKey " + apiKey))
		   .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()))
		   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
	}

	private static Authentication createAuthentication(final String identity, final String password, final AuthenticationType authenticationType)
	{
		final User user = new User();

		user.setId(1L);
		user.setUid(UUID.randomUUID());
		user.setCustomerId(1L);

		final Authentication authentication = new Authentication();

		authentication.setId(1L);
		authentication.setCustomerId(1L);
		authentication.setUser(user);
		authentication.setIdentity(identity);
		authentication.setType(authenticationType);

		if (!password.isBlank())
		{
			authentication.setPassword(EncryptionUtils.hash(password));
		}

		return authentication;
	}
}