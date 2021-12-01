package com.locationguru.csf.security.util;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.locationguru.csf.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Provides utilities for dealing with authentication tokens.
 */
public class TokenUtils
{
	private static final Logger logger = LogManager.getLogger(TokenUtils.class);

	/**
	 * Generates a new JWT token for request authentication.
	 *
	 * @param uid            unique identity of the request token.
	 * @param userUid        unique identity of the authenticated user.
	 * @param secretKey      signing key to be used signing the token.
	 * @param algorithm      used for signing tokens.
	 * @param creationDate   date of token creation.
	 * @param expirationDate date of token expiration.
	 * @return signed JWT token.
	 */
	public static String createToken(final UUID uid, final UUID userUid, final Key secretKey, final SignatureAlgorithm algorithm, final Date creationDate, final Date expirationDate, final ObjectMapper objectMapper)
	{
		// Creating token headers
		final Claims claims = Jwts.claims()
								  .setId(uid.toString()) // Token identity
								  .setSubject(userUid.toString()); // Identity of authenticated user

		// Generating JWT token
		return Jwts.builder()
				   .serializeToJsonWith(new JacksonSerializer<>(objectMapper))
				   .setClaims(claims)
				   .setIssuedAt(creationDate)
				   .setExpiration(expirationDate)
				   .signWith(secretKey, algorithm)
				   .compact();
	}

	/**
	 * Parses claims provided by authentication token {@param token}.
	 *
	 * @param token      authentication token providing claims.
	 * @param secretKey  secret key used for generating authentication token.
	 * @param jsonMapper instance of {@link ObjectMapper} to be used for parsing token.
	 * @return claims provided by {@param token}.
	 */
	public static Claims parseClaims(final String token, final SecretKeySpec secretKey, final JsonMapper jsonMapper)
	{
		return Jwts.parserBuilder()
				   .deserializeJsonWith(new JacksonDeserializer<>(jsonMapper))
				   .setSigningKey(secretKey)
				   .setAllowedClockSkewSeconds(System.currentTimeMillis()) // To disable exception for expired token
				   .build().parseClaimsJws(token).getBody();
	}

	/**
	 * Returns authorization information from the request.
	 *
	 * @param request instance of {@link HttpServletRequest} containing authorization information.
	 * @return authorization information if present in the request, otherwise empty {@link String}.
	 */
	public static String getAuthorization(final HttpServletRequest request)
	{
		return StringUtils.defaultString(request.getHeader("Authorization")).strip();
	}

	/**
	 * Returns authentication information from the request.
	 *
	 * @return authorization information if present in the current security context.
	 */
	public static Authentication getAuthentication()
	{
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * Retrieves information of authenticated user from the request context.
	 *
	 * @return information of authenticated user.
	 */
	public static User getAuthenticatedUser()
	{
		return (User) getAuthentication().getPrincipal();
	}

	/**
	 * Retrieves authentication token identity from the request context.
	 *
	 * @return authentication token identity.
	 */
	public static UUID getTokenIdentity()
	{
		return (UUID) SecurityContextHolder.getContext().getAuthentication().getCredentials();
	}

	/**
	 * Retrieves information of authenticated user from the request.
	 *
	 * @param request instance of {@link HttpServletRequest} containing authentication information.
	 * @return information of authenticated user.
	 */
	public static User getAuthenticatedUser(final HttpServletRequest request)
	{
		return (User) request.getUserPrincipal();
	}
}
