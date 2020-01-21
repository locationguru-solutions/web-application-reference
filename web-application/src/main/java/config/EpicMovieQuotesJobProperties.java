package config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.job-scheduler.epic-movie-quotes-job")
public class EpicMovieQuotesJobProperties
{
	private boolean enabled;
	private String schedule;
	private String sourceFile;

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
	}

	public String getSchedule()
	{
		return schedule;
	}

	public void setSchedule(final String schedule)
	{
		this.schedule = schedule;
	}

	public String getSourceFile()
	{
		return sourceFile;
	}

	public void setSourceFile(final String sourceFile)
	{
		this.sourceFile = sourceFile;
	}
}
