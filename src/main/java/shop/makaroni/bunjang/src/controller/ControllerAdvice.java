package shop.makaroni.bunjang.src.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.makaroni.bunjang.src.response.BeanErrorResponse;
import shop.makaroni.bunjang.src.response.ErrorResponse;
import shop.makaroni.bunjang.src.response.exception.AlreadyDeletedException;
import shop.makaroni.bunjang.src.response.exception.InvalidInputEx;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static shop.makaroni.bunjang.src.response.ErrorCode.ALREADY_DELETED_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.INVALID_INPUT_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.MISSING_PARAMETER_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.NO_SUCH_ELEMENT_EXCEPTION;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> parameterExHandler(MissingServletRequestParameterException e, HttpServletRequest request) {
		printLog(e, request);
		return ResponseEntity.status(MISSING_PARAMETER_EXCEPTION.getStatus())
				.body(ErrorResponse.of(MISSING_PARAMETER_EXCEPTION, request.getRequestURI()));
	}

	@ExceptionHandler(InvalidInputEx.class)
	public ResponseEntity<ErrorResponse> invalidInputExHandler(InvalidInputEx e, HttpServletRequest request) {
		printLog(e, request);
		return ResponseEntity.status(INVALID_INPUT_EXCEPTION.getStatus())
				.body(ErrorResponse.of(INVALID_INPUT_EXCEPTION, request.getRequestURI()));
	}

	@ExceptionHandler(AlreadyDeletedException.class)
	public ResponseEntity<ErrorResponse> alreadyDeletedExHandler(AlreadyDeletedException e, HttpServletRequest request) {
		printLog(e, request);
		return ResponseEntity.status(ALREADY_DELETED_EXCEPTION.getStatus())
				.body(ErrorResponse.of(ALREADY_DELETED_EXCEPTION, request.getRequestURI()));
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> noSuchElementExHandler(NoSuchElementException e, HttpServletRequest request) {
		printLog(e, request);
		return ResponseEntity.status(NO_SUCH_ELEMENT_EXCEPTION.getStatus())
				.body(ErrorResponse.of(NO_SUCH_ELEMENT_EXCEPTION, request.getRequestURI()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<BeanErrorResponse>> noSuchElementExHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
		printLog(e, request);
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		return ResponseEntity.badRequest()
				.body(fieldErrors.stream().map(BeanErrorResponse::of).collect(Collectors.toList()));
	}

	private void printLog(Exception e, HttpServletRequest request) {
		log.warn(e.getClass() + " 발생");
		log.warn("errors = {}", e.getStackTrace());
		log.warn("path = {}", request.getRequestURI());
	}
}
