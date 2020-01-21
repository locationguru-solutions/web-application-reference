package config;

import com.locationguru.csf.job.EpicMovieQuotesJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

@Configuration
public class QuartzJobConfiguration
{
	@Bean
	@ConditionalOnProperty(prefix = "application.job-scheduler.epic-movie-quotes-job", name = "enabled", havingValue = "true")
	public CronTriggerFactoryBean billingJobDetail(final EpicMovieQuotesJobProperties properties)
	{
		return createTriggerFactoryBean(EpicMovieQuotesJob.class, properties.getSchedule());
	}

	private static CronTriggerFactoryBean createTriggerFactoryBean(final Class<? extends Job> jobClass, final String jobSchedule)
	{
		final String jobName = jobClass.getSimpleName();
		final JobDetail job = JobBuilder.newJob(jobClass)
										.withIdentity(jobName, jobName + "Group")
										.storeDurably()
										.build();

		final CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();

		trigger.setJobDetail(job);
		trigger.setCronExpression(jobSchedule);

		return trigger;
	}
}
