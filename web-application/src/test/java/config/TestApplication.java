package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;

/**
 * Provides start up configuration of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = { "config", "com.locationguru" }, exclude = { HazelcastAutoConfiguration.class, QuartzAutoConfiguration.class })
public class TestApplication
		extends Application
{
	private static final Logger logger = LogManager.getLogger(TestApplication.class);


}
