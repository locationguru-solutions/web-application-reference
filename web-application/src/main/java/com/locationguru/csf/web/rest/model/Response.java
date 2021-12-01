package com.locationguru.csf.web.rest.model;

import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;

/**
 * A common wrapper for enforcing consistent response format.
 *
 * @param <T> type of the result(s) in response.
 */
@XmlRootElement(name = "response")
public class Response<T>
{
	@XmlElement(name = "code")
	private String code;

	@XmlElement(name = "message")
	private String message;

	@XmlElement(name = "result")
	private T result;

	@XmlElement(name = "results")
	private List<T> results;

	public Response()
	{
		this(HttpStatus.OK);
	}

	public Response(final String code, final String message)
	{
		this.code = code;
		this.message = message;
	}

	public Response(final HttpStatus httpStatus)
	{
		this.code = Integer.toString(httpStatus.value());
		this.message = httpStatus.getReasonPhrase();
	}

	public Response(final HttpStatus httpStatus, final String message)
	{
		this.code = Integer.toString(httpStatus.value());
		this.message = message;
	}

	public Response(final HttpStatus httpStatus, final T result)
	{
		this(httpStatus);
		this.result = result;
	}

	public Response(final HttpStatus httpStatus, final String message, final T result)
	{
		this(httpStatus, message);
		this.result = result;
	}

	public Response(final HttpStatus httpStatus, final List<T> results)
	{
		this(httpStatus);
		this.results = results;
	}

	public Response(final HttpStatus httpStatus, final String message, List<T> results)
	{
		this(httpStatus, message);
		this.results = results;
	}

	public static <T> Response<T> ok(final T result)
	{
		return new Response<>(HttpStatus.OK, result);
	}

	public static <T> Response<T> ok(final List<T> results)
	{
		return new Response<>(HttpStatus.OK, results);
	}

	public static <T> Response<T> ok()
	{
		return new Response<>(HttpStatus.OK);
	}

	public static <T> Response<T> badRequest(final String message)
	{
		return new Response<>(HttpStatus.BAD_REQUEST, message);
	}

	public static <T> Response<T> of(final HttpStatus status)
	{
		return new Response<>(status);
	}

	public static <T> Response<T> of(final HttpStatus status, final String message)
	{
		return new Response<>(status, message);
	}

	public static <T> Response<T> of(final String code, final String message)
	{
		return new Response<>(code, message);
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(final String message)
	{
		this.message = message;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(final String code)
	{
		this.code = code;
	}

	public T getResult()
	{
		return result;
	}

	public void setResult(final T result)
	{
		this.result = result;
	}

	public List<T> getResults()
	{
		return results;
	}

	public void setResults(final List<T> results)
	{
		this.results = results;
	}
}
