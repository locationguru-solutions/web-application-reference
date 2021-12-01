package com.locationguru.csf.web.rest.model;

import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "AuthenticationResponse")
public class AuthenticationResult
{
	private static final Logger logger = LogManager.getLogger(AuthenticationResult.class);

	@XmlElement(name = "privileges")
	private List<String> privileges;

	public List<String> getPrivileges()
	{
		return privileges;
	}

	public void setPrivileges(final List<String> privileges)
	{
		this.privileges = privileges;
	}
}
