package config;

import config.support.AbstractQuartzJobProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.job-scheduler.token-cleanup-job")
public class TokenCleanupJobProperties
		extends AbstractQuartzJobProperties
{
	/**
	 * Number of days for which authentication token history has to be maintained.
	 */
	private Integer tokenRetentionPeriod;

	public Integer getTokenRetentionPeriod()
	{
		return tokenRetentionPeriod;
	}

	public void setTokenRetentionPeriod(final Integer tokenRetentionPeriod)
	{
		this.tokenRetentionPeriod = tokenRetentionPeriod;
	}
}
