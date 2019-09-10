package com.locationguru.csf.web.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

public class ValidationException
		extends RuntimeException
{
	private static final Logger logger = LogManager.getLogger(ValidationException.class);

	private final String code;

	public ValidationException(final Integer code, final String message)
	{
		super(message);
		this.code = String.valueOf(code);
	}

	public ValidationException(final String code, final String message)
	{
		super(message);
		this.code = code;
	}

	public ValidationException(final HttpStatus status)
	{
		this(Integer.toString(status.value()), status.getReasonPhrase());
	}

	public ValidationException(final HttpStatus status, final String message)
	{
		this(Integer.toString(status.value()), message);
	}

	public ValidationException(final String message)
	{
		this("", message);
	}

	public String getCode()
	{
		return code;
	}
}
