package com.locationguru.csf.service;

import java.util.*;

import com.locationguru.csf.model.ApplicationConfiguration;
import com.locationguru.csf.repository.ApplicationConfigurationRepository;
import com.locationguru.support.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
class ApplicationConfigurationServiceTest
		extends BaseTest
{
	private static final Logger logger = LogManager.getLogger(ApplicationConfigurationServiceTest.class);

	private final ApplicationConfigurationService service;

	@MockBean
	private ApplicationConfigurationRepository repository;

	@Autowired
	public ApplicationConfigurationServiceTest(final ApplicationConfigurationService service)
	{
		this.service = service;
	}

	private ApplicationConfiguration setupMockConfiguration(final String property, final String mockValue)
	{
		final ApplicationConfiguration mockConfiguration = newConfiguration(property, mockValue);

		Mockito.when(repository.findByProperty(property)).thenReturn(mockConfiguration);

		return mockConfiguration;
	}

	@Test
	public void findAll()
	{
		final List<ApplicationConfiguration> mockConfigurations = newConfigurations(5);
		Mockito.when(repository.findAll()).thenReturn(mockConfigurations);

		final List<ApplicationConfiguration> configurations = service.findAll();

		Assertions.assertNotNull(configurations);
		Assertions.assertEquals(5, configurations.size());
	}

	@Test
	public void getStringValue()
	{
		final String property = "mock.property", mockValue = "Awesome Application";

		this.setupMockConfiguration(property, mockValue);

		final String value = service.getStringValue(property);

		Assertions.assertNotNull(value);
		Assertions.assertNotEquals(0, value.length());
		Assertions.assertEquals(mockValue, value);
	}

	@Test
	public void getIntegerValue()
	{
		final String property = "mock.property", mockValue = "117";

		this.setupMockConfiguration(property, mockValue);

		final Integer value = service.getIntegerValue(property);

		Assertions.assertNotNull(value);
		Assertions.assertEquals(117, value);
	}

	@Test
	public void getLongValue()
	{
		final String property = "mock.property", mockValue = "1568362553";

		this.setupMockConfiguration(property, mockValue);

		final Long value = service.getLongValue(property);

		Assertions.assertNotNull(value);
		Assertions.assertEquals(1568362553L, value);
	}

	@Test
	public void getDoubleValue()
	{
		final String property = "mock.property", mockValue = "156.8362553";

		this.setupMockConfiguration(property, mockValue);

		final Double value = service.getDoubleValue(property);

		Assertions.assertNotNull(value);
		Assertions.assertEquals(156.8362553D, value);
	}

	private static List<ApplicationConfiguration> newConfigurations(final int count)
	{
		final List<ApplicationConfiguration> configurations = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			configurations.add(newConfiguration());
		}

		return configurations;
	}

	private static ApplicationConfiguration newConfiguration()
	{
		final ApplicationConfiguration configuration = new ApplicationConfiguration();

		final String value = UUID.randomUUID().toString();

		configuration.setProperty(value.split("-")[4]);
		configuration.setValue(value);

		return configuration;
	}

	private static ApplicationConfiguration newConfiguration(final String property, final String value)
	{
		final ApplicationConfiguration configuration = new ApplicationConfiguration();

		configuration.setProperty(property);
		configuration.setValue(value);

		return configuration;
	}
}