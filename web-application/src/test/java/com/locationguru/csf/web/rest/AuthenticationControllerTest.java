package com.locationguru.csf.web.rest;

import java.util.UUID;

import com.locationguru.csf.model.Authentication;
import com.locationguru.csf.model.User;
import com.locationguru.csf.model.support.AuthenticationType;
import com.locationguru.csf.repository.*;
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

		final Authentication authentication = new Authentication();

		final User user = new User();

		user.setId(1L);
		user.setUid(UUID.randomUUID());
		user.setCustomerId(1L);

		authentication.setId(1L);
		authentication.setCustomerId(1L);
		authentication.setUser(user);
		authentication.setIdentity(username);
		authentication.setType(AuthenticationType.USER_NAME);
		authentication.setPassword(EncryptionUtils.hash(password));

		Mockito.when(repository.findByIdentityAndType(username, AuthenticationType.USER_NAME)).thenReturn(authentication);

		final MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/authentications/login")
																   .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
																   .param("username", username)
																   .param("password", password))
									.andExpect(MockMvcResultMatchers.status().isOk())
									.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
									.andExpect(header().exists("Authorization"))
									.andReturn();

		final String authorization = result.getResponse().getHeader("Authorization");

		Assertions.assertNotNull(authorization);
		Assertions.assertNotEquals(0, authorization.length());

		logger.info("Authorization : {}", authorization);

		this.authorization = authorization;
		this.user = user;
	}

	@Test
	@Order(2)
	void logout() throws Exception
	{
		Mockito.when(userRepository.findByUid(user.getUid())).thenReturn(user);

		mvc.perform(MockMvcRequestBuilders.post("/authentications/logout")
										  .header("Authorization", authorization))
		   .andExpect(MockMvcResultMatchers.status().isOk());
	}
}