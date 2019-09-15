package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = { "com.locationguru.csf.model", "com.locationguru.reference.app.model" })
public class HibernateConfiguration
{
	private static final Logger logger = LogManager.getLogger(HibernateConfiguration.class);

}
