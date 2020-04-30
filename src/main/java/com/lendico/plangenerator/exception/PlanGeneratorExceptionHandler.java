package com.lendico.plangenerator.exception;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.lendico.plangenerator.util.Constants;

@ControllerAdvice
public class PlanGeneratorExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanGeneratorExceptionHandler.class);

	@ExceptionHandler(PlanGeneratorException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException(PlanGeneratorException ex,
			WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), Constants.PLAN_GENERATOR_EXCEPTION_CODE,
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ApplicationParseException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(ApplicationParseException ex) {
		LOGGER.error("Exception ApplicationParseException :: {} ", ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(Constants.APPLICATION_PARSE_EXCEPTION_CODE);
		error.setErrorDesc(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", HttpStatus.BAD_REQUEST);
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {
		LOGGER.error("Exception HttpMessageNotReadableException :: {} ", ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(400);
		error.setErrorDesc(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
