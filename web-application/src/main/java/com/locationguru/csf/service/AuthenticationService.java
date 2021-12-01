package com.locationguru.csf.service;

import java.sql.Timestamp;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;

import com.locationguru.csf.model.Authentication;
import com.locationguru.csf.model.Token;
import com.locationguru.csf.model.support.AuthenticationType;
import com.locationguru.csf.model.support.TokenStatus;
import com.locationguru.csf.repository.AuthenticationRepository;
import com.locationguru.csf.security.support.ApiKeyAuthentication;
import com.locationguru.csf.security.support.JwtAuthentication;
import com.locationguru.csf.security.util.TokenUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService
{
	private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

	private final AuthenticationRepository repository;
	private final TokenService tokenService;

	@Autowired
	public AuthenticationService(final AuthenticationRepository repository, final TokenService tokenService)
	{
		this.repository = repository;
		this.tokenService = tokenService;
	}

	@Transactional(readOnly = true)
	public Authentication findByUsername(final String userName)
	{
		return repository.findByIdentityAndType(userName, AuthenticationType.USER_NAME);
	}

	@Transactional(readOnly = true)
	public Authentication findByApiKey(final String apiKey)
	{
		return repository.findByIdentityAndType(apiKey, AuthenticationType.API_KEY);
	}

	@Transactional
	public void logout(final HttpServletRequest request)
	{
		final var authentication = TokenUtils.getAuthentication();

		if (authentication instanceof ApiKeyAuthentication)
		{
			throw new UnsupportedOperationException("API key authentication does not support logout operation");
		}

		final String tokenString = ((JwtAuthentication) authentication).getTokenString();
		final Token sessionToken = this.tokenService.findByIdentity((UUID) authentication.getCredentials(), tokenString);
		final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

		sessionToken.setExpirationDate(currentTimestamp);
		sessionToken.setExpirationTimestamp(currentTimestamp);

		sessionToken.setStatus(TokenStatus.EXPIRED);
		sessionToken.setIsActive(Boolean.FALSE);
	}
}
