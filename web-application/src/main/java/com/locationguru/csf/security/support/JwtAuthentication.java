package com.locationguru.csf.security.support;

import java.util.Collection;

import com.locationguru.csf.model.support.AuthenticationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthentication
		extends AbstractTokenAuthentication
{
	private static final Logger logger = LogManager.getLogger(JwtAuthentication.class);

	private String tokenString;

	public JwtAuthentication(final Object principal, final Object credentials)
	{
		super(AuthenticationType.JSON_WEB_TOKEN,principal, credentials);
	}

	public JwtAuthentication(final Object principal, final Object credentials, final Collection<? extends GrantedAuthority> authorities)
	{
		super(AuthenticationType.JSON_WEB_TOKEN, principal, credentials, authorities);
	}

	public String getTokenString()
	{
		return tokenString;
	}

	public void setTokenString(final String tokenString)
	{
		this.tokenString = tokenString;
	}
}
