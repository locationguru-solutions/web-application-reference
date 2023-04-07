package com.locationguru.csf.web.rest.model;

import java.sql.Timestamp;
import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.locationguru.csf.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "AuthenticationResponse")
public class AuthenticationResult
{
	private static final Logger logger = LogManager.getLogger(AuthenticationResult.class);

	@XmlElement(name = "user")
	private User user;

	@XmlElement(name = "privileges")
	private List<String> privileges;

	@XmlElement(name = "loginTimestamp")
	private Timestamp loginTimestamp;

	@XmlElement(name = "expirationTimestamp")
	private Timestamp expirationTimestamp;

	public User getUser()
	{
		return this.user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}

	public List<String> getPrivileges()
	{
		return this.privileges;
	}

	public void setPrivileges(final List<String> privileges)
	{
		this.privileges = privileges;
	}

	public Timestamp getLoginTimestamp()
	{
		return this.loginTimestamp;
	}

	public void setLoginTimestamp(final Timestamp loginTimestamp)
	{
		this.loginTimestamp = loginTimestamp;
	}

	public Timestamp getExpirationTimestamp()
	{
		return this.expirationTimestamp;
	}

	public void setExpirationTimestamp(final Timestamp expirationTimestamp)
	{
		this.expirationTimestamp = expirationTimestamp;
	}
}
