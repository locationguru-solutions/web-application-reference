package config;

import javax.servlet.http.HttpServletResponse;

import com.locationguru.csf.web.rest.model.Response;
import com.locationguru.csf.web.support.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
		extends ResponseEntityExceptionHandler
{
	private static final Logger logger = LogManager.getLogger(RestExceptionHandler.class);

	/**
	 * Catches all exceptions of type {@link ValidationException}.
	 *
	 * @param e instance of {@link ValidationException} occurred during request processing.
	 * @return appropriate {@link ResponseEntity} for {@link Exception} being handled.
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Response<Void>> processValidationException(final ValidationException e, final HttpServletResponse response)
	{
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		return ResponseEntity.badRequest().body(Response.of(HttpStatus.BAD_REQUEST, e.getMessage()));
	}

	/**
	 * Catches all exceptions of type {@link MethodArgumentTypeMismatchException}.
	 *
	 * @param e instance of {@link MethodArgumentTypeMismatchException} occurred during request processing.
	 * @return appropriate {@link ResponseEntity} for {@link Exception} being handled.
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Response<Void>> processNumberFormatException(final MethodArgumentTypeMismatchException e, final HttpServletResponse response)
	{
		final StringBuilder message = new StringBuilder();

		final String parameter = e.getName();
		final Object value = e.getValue();
		final Class<?> valueType = value == null ? null : value.getClass(), expectedType = e.getParameter().getParameterType();

		logger.debug("Type mismatch for parameter '{}' with value '{}'. Expected '{}' but received '{}'", parameter, value, expectedType, valueType);

		message.append("Invalid value '").append(value).append("' ");

		if (value != null) { message.append("of type '").append(valueType.getSimpleName()).append("' "); }

		message.append("for parameter '").append(parameter).append("'. Type '").append(expectedType.getSimpleName()).append("' was expected.");

		// FIXME Possible bug
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return ResponseEntity.badRequest().body(Response.of(HttpStatus.BAD_REQUEST, message.toString()));
	}

	/**
	 * 'Catch all' handler for all the unhandled exceptions.
	 *
	 * @param e instance of {@link Exception} occurred during request processing.
	 * @return appropriate {@link ResponseEntity} for {@link Exception} being handled.
	 */
	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<Response<Void>> processException(final UnsupportedOperationException e, final HttpServletResponse response)
	{
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.of(HttpStatus.FORBIDDEN, e.getMessage()));
	}


	/**
	 * 'Catch all' handler for all the unhandled exceptions.
	 *
	 * @param e instance of {@link Exception} occurred during request processing.
	 * @return appropriate {@link ResponseEntity} for {@link Exception} being handled.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response<Void>> processException(final Exception e, final HttpServletResponse response)
	{
		logger.error(e.getMessage(), e);
		logger.error(e.getMessage(), (Object[]) e.getSuppressed());

		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.of(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error has occurred while processing your request"));
	}

}
