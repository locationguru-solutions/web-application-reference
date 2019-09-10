package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Provides start up configuration of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = { "config", "com.locationguru" }, exclude = { HazelcastAutoConfiguration.class })
public class Application
		extends SpringBootServletInitializer
{
	private static final Logger logger = LogManager.getLogger(Application.class);

	/**
	 * Configures {@link #main} as starting point of the application.
	 *
	 * @param builder instance.
	 * @return configured builder instance.
	 */
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder)
	{
		return builder.sources(Application.class);
	}

	/**
	 * Application starting point.
	 *
	 * @param args program arguments.
	 */
	public static void main(final String[] args)
	{
		// Creating spring application
		final SpringApplication application = new SpringApplication(Application.class);
		application.setAddCommandLineProperties(true); // Enable command line properties

		// Creating application context and starting the application
		final ConfigurableApplicationContext applicationContext = application.run(args);
	}
}
