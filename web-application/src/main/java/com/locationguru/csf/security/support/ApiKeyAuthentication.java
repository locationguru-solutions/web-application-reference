package com.locationguru.csf.security.support;

import java.util.Collection;

import com.locationguru.csf.model.support.AuthenticationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;

public class ApiKeyAuthentication
		extends AbstractTokenAuthentication
{
	private static final Logger logger = LogManager.getLogger(ApiKeyAuthentication.class);

	private String apiKey;

	public ApiKeyAuthentication(final Object principal, final Object credentials)
	{
		super(AuthenticationType.API_KEY, principal, credentials);
	}

	public ApiKeyAuthentication(final Object principal, final Object credentials, final Collection<? extends GrantedAuthority> authorities)
	{
		super(AuthenticationType.API_KEY, principal, credentials, authorities);
	}

	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(final String apiKey)
	{
		this.apiKey = apiKey;
	}
}
