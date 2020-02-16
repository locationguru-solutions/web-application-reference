package config.support;

public abstract class AbstractQuartzJobProperties
{
	protected boolean enabled;
	protected String schedule;

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
}
