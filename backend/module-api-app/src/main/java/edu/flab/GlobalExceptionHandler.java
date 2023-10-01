package edu.flab;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import edu.flab.exception.AuthenticationException;
import edu.flab.exception.BusinessException;
import edu.flab.web.response.ErrorCode;
import edu.flab.web.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 컨트롤러가 javax.validation.Valid or @Validated 으로 유효성 체크, 실패하였을 경우 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 컨트롤러가 입력값을 객체필드로 binding, type 일치하지 않을 경우 발생
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 지원하지 않는 HTTP method 호출시 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * 요청자가 로그인 인증에 실패했을 경우 발생
	 */
	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ACCESS_DENIED);
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 비지니스 예외가 발생
	 */
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(errorResponse, e.getErrorCode().getHttpStatusCode());
	}

	/**
	 * 조회 서비스를 수행했으나, 결과를 찾지 못했을 경우 발생
	 */
	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<ErrorResponse> handleNoSuchException(NoSuchElementException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Java bean validation 실패시 발생
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
