package com.locationguru.csf.web.rest;

import java.util.Collections;

import com.locationguru.csf.model.Authentication;
import com.locationguru.csf.model.Token;
import com.locationguru.csf.security.util.EncryptionUtils;
import com.locationguru.csf.service.AuthenticationService;
import com.locationguru.csf.service.TokenService;
import com.locationguru.csf.web.rest.model.AuthenticationResult;
import com.locationguru.csf.web.rest.model.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/authentications")
public class AuthenticationController
{
	private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

	private final AuthenticationService authenticationService;
	private final TokenService tokenService;

	@Autowired
	public AuthenticationController(final AuthenticationService authenticationService, final TokenService tokenService)
	{
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
	}

	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<Response<AuthenticationResult>> login(final HttpServletRequest request, final HttpServletResponse response)
	{
		final String username = StringUtils.defaultString(request.getParameter("username")).strip();
		final String password = StringUtils.defaultString(request.getParameter("password")).strip();

		if (username.isBlank() || password.isBlank())
		{
			return ResponseEntity.badRequest().body(Response.badRequest("Username and password combination doesn't match."));
		}

		final Authentication authentication = this.authenticationService.findByUsername(username);

		if (authentication == null)
		{
			return ResponseEntity.badRequest().body(Response.badRequest("Username and password combination doesn't match."));
		}

		if (!EncryptionUtils.match(password, authentication.getPassword()))
		{
			return ResponseEntity.badRequest().body(Response.badRequest("Username and password combination doesn't match."));
		}

		// Persisting token for future validations
		final Token token = this.tokenService.create(authentication);

		// Setting authentication token in response
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Authorization", "Bearer " + token.getIdentity());

		logger.trace("User '{}' authenticated via {}", authentication.getUser().getUid(), authentication.getType());

		final AuthenticationResult result = new AuthenticationResult();

		// Populating user privileges
		result.setPrivileges(Collections.emptyList());

		return ResponseEntity.ok(Response.ok(result));
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<Response<Void>> logout(final HttpServletRequest request)
	{
		this.authenticationService.logout(request);

		return ResponseEntity.ok(Response.ok());
	}

	@PostMapping(value = "/check")
	public ResponseEntity<Response<Void>> check()
	{
		return ResponseEntity.ok(Response.ok());
	}
}
