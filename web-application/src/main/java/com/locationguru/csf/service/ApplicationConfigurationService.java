package com.locationguru.csf.service;

import java.util.List;
import java.util.function.Function;

import com.locationguru.csf.model.ApplicationConfiguration;
import com.locationguru.csf.repository.ApplicationConfigurationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationConfigurationService
{
	private static final Logger logger = LogManager.getLogger(ApplicationConfigurationService.class);

	private final ApplicationConfigurationRepository configurationRepository;

	@Autowired
	public ApplicationConfigurationService(final ApplicationConfigurationRepository configurationRepository)
	{
		this.configurationRepository = configurationRepository;
	}

	@Transactional(readOnly = true)
	public List<ApplicationConfiguration> findAll()
	{
		return configurationRepository.findAll();
	}

	@Transactional(readOnly = true)
	public String getStringValue(final String property)
	{
		final ApplicationConfiguration configuration = configurationRepository.findByProperty(property);

		if (configuration == null) { return null; }

		return configuration.getValue();
	}

	@Transactional(readOnly = true)
	public Integer getIntegerValue(final String property)
	{
		return getValue(property, Integer::valueOf);
	}

	@Transactional(readOnly = true)
	public Long getLongValue(final String property)
	{
		return getValue(property, Long::valueOf);
	}

	@Transactional(readOnly = true)
	public Double getDoubleValue(final String property)
	{
		return getValue(property, Double::valueOf);
	}

	private <T> T getValue(final String property, final Function<String, T> converter)
	{
		final String value = getStringValue(property);

		if (value == null) { return null; }

		return converter.apply(value);
	}

}
