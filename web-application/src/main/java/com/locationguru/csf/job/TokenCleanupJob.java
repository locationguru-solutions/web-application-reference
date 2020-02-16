package com.locationguru.csf.job;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.locationguru.csf.service.TokenService;
import config.TokenCleanupJobProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * This job expires the invalid authentication tokens and
 * permanently deletes the tokens which are past their retention period.
 */
public class TokenCleanupJob
		extends QuartzJobBean
{
	private static final Logger logger = LogManager.getLogger(TokenCleanupJob.class);

	private final TokenCleanupJobProperties properties;
	private final TokenService tokenService;

	public TokenCleanupJob(final TokenCleanupJobProperties properties, final TokenService tokenService)
	{
		this.properties = properties;
		this.tokenService = tokenService;
	}

	@Override
	protected void executeInternal(final JobExecutionContext context)
	{
		logger.info("Starting token cleanup job ..");

		// Updating expiration status
		final Integer rows = tokenService.updateExpirationStatus();

		logger.info("{} tokens have been expired ..", rows);

		// Getting applicable dates for which expired tokens can be deleted
		final Timestamp expirationTimestamp = Timestamp.valueOf(LocalDate.now().minusDays(properties.getTokenRetentionPeriod()).atStartOfDay());
		final List<Date> expirationDates = tokenService.findExpirationDates(expirationTimestamp);

		logger.info("Found {} date(s) with tokens past retention period of '{}' ..", expirationDates.size(), expirationTimestamp);

		for (final Date expirationDate : expirationDates)
		{
			final Integer deletedRows = tokenService.deleteTokensWithExpirationDate(expirationDate);

			logger.info("{} expired token deleted ..", deletedRows);
		}

		logger.info("Token cleanup job completed ..");
	}
}
