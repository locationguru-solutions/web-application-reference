package com.locationguru.csf.repository;

import java.util.List;

import com.locationguru.csf.model.ApplicationConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationConfigurationRepository
		extends BaseRepository<ApplicationConfiguration>
{
	private static final Logger logger = LogManager.getLogger(ApplicationConfigurationRepository.class);

	public List<ApplicationConfiguration> findAll()
	{
		final String queryString = "SELECT a FROM ApplicationConfiguration a ORDER BY a.id";

		return db.createQuery(queryString, ApplicationConfiguration.class)
				 .getResultList();
	}

	public ApplicationConfiguration findByProperty(final String property)
	{
		final String queryString = "SELECT a FROM ApplicationConfiguration a WHERE a.property = :property ORDER BY a.id";

		return first(db.createQuery(queryString, ApplicationConfiguration.class)
					   .setParameter("property", property)
					   .setMaxResults(1)
					   .getResultList());
	}

}
