package shop.makaroni.bunjang.src.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.makaroni.bunjang.src.response.ErrorResponse;
import shop.makaroni.bunjang.src.response.exception.InvalidInputEx;

import javax.servlet.http.HttpServletRequest;

import static shop.makaroni.bunjang.src.response.ErrorCode.INVALID_INPUT_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.MISSING_PARAMETER_EXCEPTION;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> parameterExHandler(MissingServletRequestParameterException e, HttpServletRequest request) {
		return ResponseEntity.status(MISSING_PARAMETER_EXCEPTION.getStatus())
				.body(ErrorResponse.of(MISSING_PARAMETER_EXCEPTION, request.getRequestURI()));
	}

	@ExceptionHandler(InvalidInputEx.class)
	public ResponseEntity<ErrorResponse> invalidInputExHandler(InvalidInputEx e, HttpServletRequest request) {
		return ResponseEntity.status(INVALID_INPUT_EXCEPTION.getStatus())
				.body(ErrorResponse.of(INVALID_INPUT_EXCEPTION, request.getRequestURI()));
	}
}
