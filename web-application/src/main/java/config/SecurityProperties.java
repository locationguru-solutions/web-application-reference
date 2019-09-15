package config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties
{
	/**
	 * Secret key for signing json tokens.
	 */
	private String secretKey;

	/**
	 * Duration for which JWT token will be valid.
	 */
	private Duration tokenValidity;

	public String getSecretKey()
	{
		return secretKey;
	}

	public void setSecretKey(final String secretKey)
	{
		this.secretKey = secretKey;
	}

	public Duration getTokenValidity()
	{
		return tokenValidity;
	}

	public void setTokenValidity(final Duration tokenValidity)
	{
		this.tokenValidity = tokenValidity;
	}
}
