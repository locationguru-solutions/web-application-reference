package config;

import config.support.AbstractQuartzJobProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.job-scheduler.epic-movie-quotes-job")
public class EpicMovieQuotesJobProperties
		extends AbstractQuartzJobProperties
{
	private String sourceFile;

	public String getSourceFile()
	{
		return sourceFile;
	}

	public void setSourceFile(final String sourceFile)
	{
		this.sourceFile = sourceFile;
	}
}
