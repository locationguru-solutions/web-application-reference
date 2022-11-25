package com.locationguru.csf.persistence.support;

import java.sql.Types;

import org.hibernate.dialect.H2Dialect;

/**
 * Adds UUID support to {@link H2Dialect} for testing.
 */
public class H2DialectUUID
		extends H2Dialect
{
	public H2DialectUUID()
	{
		super();

		this.registerColumnType(Types.BINARY, "UUID");
	}
}