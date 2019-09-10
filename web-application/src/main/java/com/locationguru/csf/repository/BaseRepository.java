package com.locationguru.csf.repository;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseRepository<T>
{
	private static final Logger logger = LogManager.getLogger(BaseRepository.class);

	@PersistenceContext
	protected EntityManager db;

	public void create(final T entity)
	{
		db.persist(entity);
	}

	public void createAll(final List<T> entities)
	{
		for (final T entity : entities)
		{
			db.persist(entity);
		}
	}

	public void save(final T entity)
	{
		db.merge(entity);
	}

	public void saveAll(final List<T> entities)
	{
		for (final T entity : entities)
		{
			db.merge(entity);
		}
	}

	public void delete(final T entity)
	{
		db.remove(entity);
	}

	protected <Type> Type first(final List<Type> results)
	{
		return results.isEmpty() ? null : results.get(0);
	}

	public static Long toLong(final Object object)
	{
		if (object == null) { return null; }

		if (object instanceof BigInteger)
		{
			return ((BigInteger) object).longValue();
		}

		return (Long) object;
	}

}
