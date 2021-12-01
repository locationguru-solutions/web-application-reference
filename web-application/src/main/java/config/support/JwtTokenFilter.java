package config.support;

import java.io.IOException;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.locationguru.csf.model.Token;
import com.locationguru.csf.model.User;
import com.locationguru.csf.security.support.ApiKeyAuthentication;
import com.locationguru.csf.security.support.JwtAuthentication;
import com.locationguru.csf.security.util.TokenUtils;
import com.locationguru.csf.service.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.DecodingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter
		extends GenericFilterBean
{
	private static final Logger logger = LogManager.getLogger(JwtTokenFilter.class);

	private final UserService userService;
	private final TokenService tokenService;
	private final AuthenticationService authenticationService;
	private final JsonMapper jsonMapper;

	public JwtTokenFilter(final UserService userService, final TokenService tokenService, final AuthenticationService authenticationService, final JsonMapper jsonMapper)
	{
		this.userService = userService;
		this.tokenService = tokenService;
		this.authenticationService = authenticationService;
		this.jsonMapper = jsonMapper;
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException
	{
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final Authentication authentication = this.getAuthentication(request);

		if (authentication != null)
		{
			// Setting authentication information for future use
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public Authentication getAuthentication(final HttpServletRequest request)
	{
		final String authorization = TokenUtils.getAuthorization(request);
		final StringTokenizer tokenizer = new StringTokenizer(authorization, " ");
		final String type = StringUtils.defaultString(tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "").strip();
		final String authentication = StringUtils.defaultString(tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "").strip();

		if (authentication.isBlank())
		{
			return null;
		}

		switch (type)
		{
			case "Bearer":
				return this.getJwtAuthentication(authentication);

			case "ApiKey":
				return this.getApiKeyAuthentication(authentication);
		}

		return null;
	}

	private Authentication getJwtAuthentication(final String token)
	{
		try
		{
			final Claims claims = TokenUtils.parseClaims(token, tokenService.getSecretKey(), jsonMapper);

			if (claims.getExpiration().before(new Date()))
			{
				// Token has expired
				return null;
			}

			final Token sessionToken = this.tokenService.findByIdentity(UUID.fromString(claims.getId()), token);

			if (sessionToken == null)
			{
				// Unknown token
				return null;
			}

			final UUID userUid = UUID.fromString(claims.getSubject());
			final User user = this.userService.findById(userUid);

			if (user == null)
			{
				return null;
			}

			final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("User"));
			final JwtAuthentication authentication = new JwtAuthentication(user, sessionToken.getUid(), authorities);

			authentication.setTokenString(token);

			return authentication;
		}
		catch (final DecodingException | SecurityException | IllegalArgumentException e)
		{
			logger.debug("Unable to decode / parse token '{}' due to '{}'", token, e.getMessage());
			logger.trace(e.getMessage(), e);
		}
		catch (final Exception e)
		{
			logger.error("Unable to parse token '{}' due to '{}'", token, e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public Authentication getApiKeyAuthentication(final String apiKey)
	{
		final var authentication = this.authenticationService.findByApiKey(apiKey);

		if (authentication == null)
		{
			return null;
		}

		final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("User"));

		return new ApiKeyAuthentication(authentication.getUser(), apiKey, authorities);
	}
}
