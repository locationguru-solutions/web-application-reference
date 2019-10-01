package com.locationguru.csf.security.support;

import java.util.Collection;

import com.locationguru.csf.model.support.AuthenticationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public abstract class AbstractTokenAuthentication
		extends PreAuthenticatedAuthenticationToken
{
	private static final Logger logger = LogManager.getLogger(AbstractTokenAuthentication.class);

	private final AuthenticationType authenticationType;

	public AbstractTokenAuthentication(final AuthenticationType authenticationType, final Object principal, final Object credentials)
	{
		super(principal, credentials);
		this.authenticationType = authenticationType;
	}

	public AbstractTokenAuthentication(final AuthenticationType authenticationType, final Object principal, final Object credentials, final Collection<? extends GrantedAuthority> authorities)
	{
		super(principal, credentials, authorities);
		this.authenticationType = authenticationType;
	}

	public AuthenticationType getAuthenticationType()
	{
		return authenticationType;
	}
}
