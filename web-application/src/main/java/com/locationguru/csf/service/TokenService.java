package com.locationguru.csf.service;

import java.sql.Timestamp;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;

import com.locationguru.csf.model.*;
import com.locationguru.csf.model.support.TokenStatus;
import com.locationguru.csf.repository.TokenRepository;
import com.locationguru.csf.security.util.TokenUtils;
import config.SecurityProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService
{
	private final TokenRepository repository;
	private final SecurityProperties securityProperties;

	private final SecretKeySpec secretKey;

	@Autowired
	public TokenService(final TokenRepository repository, final SecurityProperties securityProperties)
	{
		this.repository = repository;
		this.securityProperties = securityProperties;

		final byte[] secretKeyBytes = Base64.getDecoder().decode(securityProperties.getSecretKey());
		this.secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
	}

	@Transactional(readOnly = true)
	public Token findByIdentity(final UUID uid, final String identity)
	{
		return repository.findActiveTokenByIdentity(uid, identity);
	}

	@Transactional
	public void save(final Token token)
	{
		repository.save(token);
	}

	@Transactional
	public Token create(final Authentication authentication)
	{
		final User user = authentication.getUser();
		final UUID uid = UUID.randomUUID();

		final Date creationDate = new Date();
		final Timestamp expirationTimestamp = new Timestamp(creationDate.getTime() + securityProperties.getTokenValidity().toMillis());
		final String tokenString = TokenUtils.createToken(uid, user.getUid(), secretKey, SignatureAlgorithm.HS512, creationDate, expirationTimestamp);

		final Token token = new Token();

		token.setUid(uid);
		token.setUserId(user.getId());
		token.setCustomerId(authentication.getCustomerId());
		token.setAuthenticationId(authentication.getId());
		token.setIdentity(tokenString);
		token.setExpectedExpirationTimestamp(expirationTimestamp);

		token.setStatus(TokenStatus.ACTIVE);
		token.setIsActive(Boolean.TRUE);

		this.create(token);

		return token;
	}

	@Transactional
	public void create(final Token token)
	{
		repository.create(token);
	}

	public SecretKeySpec getSecretKey()
	{
		return secretKey;
	}
}
