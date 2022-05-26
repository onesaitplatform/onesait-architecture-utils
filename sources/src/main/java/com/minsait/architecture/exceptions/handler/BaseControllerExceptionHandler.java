package com.minsait.architecture.exceptions.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.minsait.architecture.exceptions.BadRequestException;
import com.minsait.architecture.exceptions.NotFoundException;
import com.minsait.architecture.exceptions.OnesaitException;
import com.minsait.architecture.exceptions.UserNotFoundException;
import com.minsait.architecture.model.error.ResponseError;
import com.minsait.architecture.model.error.ResponseErrorList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class BaseControllerExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle exception.
	 *
	 * @param exception the exception
	 * @return the response error list
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseErrorList handleException(Exception exception) {

		log.error("Handling Exception ({}) - Catching: {}", exception.getClass().getSimpleName(), exception);

		final ResponseError error = new ResponseError("Alias", "Type", exception.getMessage());

		return new ResponseErrorList(Arrays.asList(error));
	}

	/**
	 * Handle motion exception.
	 *
	 * @param exception the exception
	 * @return the response error list
	 */
	@ExceptionHandler(value = OnesaitException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseErrorList handleOnesaitException(OnesaitException exception) {

		log.error("Handling OnesaitException ({}) - Catching: {}", exception.getClass().getSimpleName(), exception);

		final ResponseError error = new ResponseError("SERVICE_ERROR", "ERROR", exception.getMessage());

		return new ResponseErrorList(Arrays.asList(error));

	}

	/**
	 * Handle motion exception.
	 *
	 * @param exception the exception
	 * @return the response error list
	 */
	@ExceptionHandler(value = BadRequestException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseErrorList handleOnesaitException(BadRequestException exception) {

		log.error("Handling OnesaitException ({}) - Catching: {}", exception.getClass().getSimpleName(), exception);

		final ResponseError error = new ResponseError("BAD_REQUEST", "ERROR IN PARAMS", exception.getMessage());

		return new ResponseErrorList(Arrays.asList(error));

	}

	/**
	 * Handle user not found exception.
	 *
	 * @param exception the exception
	 * @return the response error list
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseErrorList handleUserNotFoundException(UserNotFoundException exception) {
		log.error("Handling UserNotFoundException ({}) - Catching: {}", exception.getClass().getSimpleName(),
				exception);

		final ResponseError error = new ResponseError("Alias user", "Type user", exception.getMessage());

		return new ResponseErrorList(Arrays.asList(error));
	}

	/**
	 * Handle not found exception.
	 *
	 * @param exception the exception
	 * @return the response error list
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseErrorList handleNotFoundException(NotFoundException exception) {
		log.error("Handling NotFoundException ({}) - Catching: {}. Id {} not found in Ontology {}",
				exception.getClass().getSimpleName(), exception, exception.getId(), exception.getOntology());

		final ResponseError error = new ResponseError("Not Found Exception", "NotFoundException",
				String.format("Id %s not found in Ontology %s ", exception.getId(), exception.getOntology()));

		return new ResponseErrorList(Arrays.asList(error));
	}

	@ExceptionHandler(org.springframework.web.client.HttpClientErrorException.class)
	@ResponseBody
	public ResponseEntity<Object> handleExceptionA(RestClientException exception) {
		log.error("Handling Exception RestClientException ({}) ", exception.getClass().getSimpleName());

		final ResponseError responseError = new ResponseError("Alias", "Type", exception.getMessage());

		HttpClientErrorException error = (HttpClientErrorException) exception;

		return new ResponseEntity<>(new ResponseErrorList(Arrays.asList(responseError)), error.getStatusCode());
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException exception, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		log.error("Handling BindException - Catching: {}", exception.getBindingResult().getAllErrors());
		final BindingResult bindingResult = exception.getBindingResult();

		final List<ResponseError> errorList = bindingResult.getAllErrors().stream()
				.map(this::objectErrorToResponseError).collect(Collectors.toList());

		return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
	}

	private ResponseError objectErrorToResponseError(ObjectError bindError) {
		return new ResponseError("Binding errors", "onesait error",
				bindError.getObjectName() + " - " + bindError.getDefaultMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Handling MethodArgumentNotValidException - Catching: {}",
				exception.getBindingResult().getAllErrors());

		return new ResponseEntity<>("Error argument", HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Handling HttpMessageNotReadableException - Catching: {}", exception.getMessage());

		return new ResponseEntity<>("Convert error", HttpStatus.BAD_REQUEST);
	}

}
